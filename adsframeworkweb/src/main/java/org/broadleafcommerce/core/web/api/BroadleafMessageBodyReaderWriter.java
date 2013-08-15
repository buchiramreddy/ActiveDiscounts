/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.broadleafcommerce.core.web.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;

import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;

import com.sun.jersey.core.impl.provider.entity.XMLListElementProvider;
import com.sun.jersey.core.impl.provider.entity.XMLRootElementProvider;
import com.sun.jersey.json.impl.provider.entity.JSONListElementProvider;
import com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider;
import com.sun.jersey.spi.inject.Injectable;


/**
 * <p>This custom MessageBodyReaderWriter was written in order to correctly handle any custom extended entities that
 * Broadleaf is aware of. The intent is to replace any paramerterized types with the correct implementations that are
 * defined in the Application Context. Once the correct generic types are replaced, the class then delegates to the
 * default XML or JSON List providers.</p>
 *
 * @author   elbertbautista
 * @see      com.sun.jersey.json.impl.provider.entity.JSONListElementProvider
 * @see      com.sun.jersey.core.impl.provider.entity.XMLListElementProvider
 * @version  $Revision$, $Date$
 */
// This class MUST be a singleton Spring Bean
@Consumes(
  value = {
    MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
    MediaType.TEXT_XML
  }
)
@Produces(
  value = {
    MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
    MediaType.TEXT_XML
  }
)
@Provider
@Scope("singleton")
public class BroadleafMessageBodyReaderWriter implements MessageBodyReader<Object>, MessageBodyWriter<Object>,
  ContextResolver<JAXBContext>, ApplicationContextAware {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static HashMap<String, Class<?>> typeMap = null;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected ApplicationContext applicationContext;

  /** DOCUMENT ME! */
  protected JAXBContext                 jaxbContext             = null;

  /** DOCUMENT ME! */
  protected JSONListElementProvider.App jsonListProvider;

  /** DOCUMENT ME! */
  protected JSONRootElementProvider.App jsonRootElementProvider;

  /** DOCUMENT ME! */
  @Context protected Providers ps;

  /** DOCUMENT ME! */
  @Context protected Injectable<SAXParserFactory> spf;

  /** DOCUMENT ME! */
  @Context protected Injectable<XMLInputFactory> xif;

  /** DOCUMENT ME! */
  protected XMLListElementProvider.App xmlListProvider;

  /** DOCUMENT ME! */
  protected XMLRootElementProvider.App xmlRootElementProvider;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.ws.rs.ext.ContextResolver#getContext(java.lang.Class)
   */
  @Override public JAXBContext getContext(final Class<?> ignored) {
    if (jaxbContext == null) {
      synchronized (this) {
        initializeTypeMap();

        if (!typeMap.isEmpty()) {
          try {
            jaxbContext = JAXBContext.newInstance(typeMap.values().toArray(new Class<?>[typeMap.size()]));
          } catch (JAXBException e) {
            throw new RuntimeException("Error creating a JAXBContext", e);
          }
        }
      }
    }

    return jaxbContext;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object, java.lang.Class, java.lang.reflect.Type,
   *       java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
   */
  @Override public long getSize(Object t, Class<?> type, Type genericType,
    Annotation[] annotations, MediaType mediaType) {
    return -1;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.ws.rs.ext.MessageBodyReader#isReadable(java.lang.Class, java.lang.reflect.Type,
   *       java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
   */
  @Override public boolean isReadable(Class<?> type, Type genericType,
    Annotation[] annotations, MediaType mediaType) {
    return isWriteable(type, genericType, annotations, mediaType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.ws.rs.ext.MessageBodyWriter#isWriteable(java.lang.Class, java.lang.reflect.Type,
   *       java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
   */
  @Override public boolean isWriteable(Class<?> type, Type genericType,
    Annotation[] annotations, MediaType mediaType) {
    Type lookupType = getLookupType(type, genericType);

    if (getApiWrapper(type, lookupType) != null) {
      return true;
    }

    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.ws.rs.ext.MessageBodyReader#readFrom(java.lang.Class, java.lang.reflect.Type,
   *       java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap,
   *       java.io.InputStream)
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Object readFrom(Class<Object> type, Type genericType,
    Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
    throws IOException, WebApplicationException {
    initializeProviders();

    Type lookupType = getLookupType(type, genericType);

    if (getApiWrapper(type, lookupType) != null) {
      genericType = getApiWrapper(type, lookupType);
    }

    if (mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
      if (Collection.class.isAssignableFrom(type)) {
        return jsonListProvider.readFrom(type, genericType,
            annotations, mediaType, httpHeaders, entityStream);
      } else if (type.isArray()) {
        // Since we've replaced the genericType param with the correct
        // implementation, we have to pass that as the first argument as
        // well because
        // if it's an array, the default list provider checks the
        // componentType of only the first argument
        return jsonListProvider.readFrom((Class) genericType,
            genericType, annotations, mediaType, httpHeaders,
            entityStream);
      } else {
        // Since we've replaced the genericType param with the correct
        // implementation, we have to pass that as the first argument as
        // well because
        // the default root element providers don't actually use
        // genericType in their implementations.
        return jsonRootElementProvider.readFrom((Class) genericType,
            genericType, annotations, mediaType, httpHeaders,
            entityStream);
      }
    } else if (mediaType.isCompatible(MediaType.APPLICATION_XML_TYPE)
          || mediaType.isCompatible(MediaType.TEXT_XML_TYPE)) {
      if (Collection.class.isAssignableFrom(type)) {
        return xmlListProvider.readFrom(type, genericType, annotations,
            mediaType, httpHeaders, entityStream);
      } else if (type.isArray()) {
        // Since we've replaced the genericType param with the correct
        // implementation, we have to pass that as the first argument as
        // well because
        // if it's an array, the default list provider checks the
        // componentType of only the first argument
        return xmlListProvider.readFrom((Class) genericType,
            genericType, annotations, mediaType, httpHeaders,
            entityStream);
      } else {
        // Since we've replaced the genericType param with the correct
        // implementation, we have to pass that as the first argument as
        // well because
        // the default root element providers don't actually use
        // genericType in their implementations.
        return xmlRootElementProvider.readFrom((Class) genericType,
            genericType, annotations, mediaType, httpHeaders,
            entityStream);
      }
    } // end if-else

    return null;
  } // end method readFrom

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
   */
  @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.ws.rs.ext.MessageBodyWriter#writeTo(java.lang.Object, java.lang.Class, java.lang.reflect.Type,
   *       java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap,
   *       java.io.OutputStream)
   */
  @Override
  @SuppressWarnings("rawtypes")
  public void writeTo(Object t, Class<?> type, Type genericType,
    Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
    OutputStream entityStream) throws IOException {
    initializeProviders();

    Type lookupType = getLookupType(type, genericType);

    if (getApiWrapper(type, lookupType) != null) {
      genericType = getApiWrapper(type, lookupType);
    }

    if (mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
      if (Collection.class.isAssignableFrom(type)) {
        jsonListProvider.writeTo(t, type, genericType, annotations,
          mediaType, httpHeaders, entityStream);
      } else if (type.isArray()) {
        // Since we've replaced the genericType param with the correct
        // implementation, we have to pass that as the first argument as
        // well because
        // if it's an array, the default list provider checks the
        // componentType of only the first argument
        jsonListProvider.writeTo(t, (Class) genericType, genericType,
          annotations, mediaType, httpHeaders, entityStream);
      } else {
        jsonRootElementProvider.writeTo(t, type, genericType,
          annotations, mediaType, httpHeaders, entityStream);
      }
    } else if (mediaType.isCompatible(MediaType.APPLICATION_XML_TYPE)
          || mediaType.isCompatible(MediaType.TEXT_XML_TYPE)) {
      if (Collection.class.isAssignableFrom(type)) {
        xmlListProvider.writeTo(t, type, genericType, annotations,
          mediaType, httpHeaders, entityStream);
      } else if (type.isArray()) {
        // Since we've replaced the genericType param with the correct
        // implementation, we have to pass that as the first argument as
        // well because
        // if it's an array, the default list provider checks the
        // componentType of only the first argument
        xmlListProvider.writeTo(t, (Class) genericType, genericType,
          annotations, mediaType, httpHeaders, entityStream);
      } else {
        xmlRootElementProvider.writeTo(t, type, genericType,
          annotations, mediaType, httpHeaders, entityStream);
      }
    } // end if-else
  } // end method writeTo

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Via the ApplicationContext we can look up exactly what implementation corresponds to the parameterized domain
   * interface and determine if that is annotated with proper JAXB annotations for serialization. We then return the
   * correct implementing Class in case of a root element, a new ParameterizedType in case of a collection, or a new
   * Array in case of an array The default providers can then handle the actual serialization of the List or Root
   * element safely.
   *
   * @param   type        DOCUMENT ME!
   * @param   lookupType  DOCUMENT ME!
   *
   * @return  via the ApplicationContext we can look up exactly what implementation corresponds to the parameterized
   *          domain interface and determine if that is annotated with proper JAXB annotations for serialization.
   */
  @SuppressWarnings("rawtypes")
  protected Type getApiWrapper(Class<?> type, Type lookupType) {
    initializeTypeMap();

    if (lookupType instanceof Class) {
      Class<?> clz = typeMap.get(((Class) lookupType).getName());

      if (clz == null) {
        return null;
      }

      if (Collection.class.isAssignableFrom(type)) {
        Type[] paramType = { clz };

        return new ParameterizedTypeImpl(paramType, type, null);
      } else if (type.isArray()) {
        return Array.newInstance(clz, 0).getClass();
      } else {
        return clz;
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   type         DOCUMENT ME!
   * @param   genericType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Type getLookupType(Class<?> type, Type genericType) {
    Type lookupType = genericType;

    if (Collection.class.isAssignableFrom(type)) {
      final ParameterizedType pt = (ParameterizedType) genericType;
      lookupType = pt.getActualTypeArguments()[0];
    } else if (type.isArray()) {
      lookupType = type.getComponentType();
    }

    return lookupType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  protected void initializeTypeMap() {
    if (typeMap == null) {
      synchronized (this.getClass()) {
        if (typeMap == null) {
          typeMap = new HashMap<String, Class<?>>();

          Map<String, Object> apiWrappers = applicationContext.getBeansWithAnnotation(XmlRootElement.class);

          for (Object obj : apiWrappers.values()) {
            typeMap.put(obj.getClass().getName(), obj.getClass());
          }
        }
      }
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private void initializeProviders() {
    if (jsonListProvider == null) {
      jsonListProvider = new JSONListElementProvider.App(ps);
    }

    if (xmlListProvider == null) {
      xmlListProvider = new XMLListElementProvider.App(xif, ps);
    }

    if (xmlRootElementProvider == null) {
      xmlRootElementProvider = new XMLRootElementProvider.App(spf, ps);
    }

    if (jsonRootElementProvider == null) {
      jsonRootElementProvider = new JSONRootElementProvider.App(ps);
    }
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  /*
   * This is based on the Sun / Oracle implementation of a similar class
   */
  protected class ParameterizedTypeImpl implements ParameterizedType {
    //~ Instance fields ------------------------------------------------------------------------------------------------

    private final Type[]   actualTypeArguments;
    private Type           ownerType;
    private final Class<?> rawType;

    //~ Constructors ---------------------------------------------------------------------------------------------------

    public ParameterizedTypeImpl(Type[] actualTypeArguments, Class<?> rawType, Type ownerType) {
      this.actualTypeArguments = actualTypeArguments;
      this.rawType             = rawType;

      if (ownerType != null) {
        this.ownerType = ownerType;
      } else {
        this.ownerType = rawType.getDeclaringClass();
      }
    }

    //~ Methods --------------------------------------------------------------------------------------------------------

    @Override public Type[] getActualTypeArguments() {
      return actualTypeArguments.clone();
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public Type getOwnerType() {
      return ownerType;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public Type getRawType() {
      return rawType;
    }
  } // end class ParameterizedTypeImpl
} // end class BroadleafMessageBodyReaderWriter
