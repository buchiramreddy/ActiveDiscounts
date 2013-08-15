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

package org.broadleafcommerce.common.cache.engine;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Id;

import org.broadleafcommerce.common.cache.Hydrated;

import org.hibernate.annotations.Cache;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class HydrationScanner implements ClassVisitor, FieldVisitor, AnnotationVisitor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final int CLASSSTAGE = 0;
  private static final int FIELDSTAGE = 1;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String                               annotation;
  private Map<String, HydrationItemDescriptor> cacheMutators = new HashMap<String, HydrationItemDescriptor>();

  private String                cacheRegion;
  @SuppressWarnings("unchecked")
  private Class                 clazz;
  @SuppressWarnings("unchecked")
  private final Class           entityClass;
  @SuppressWarnings("unchecked")
  private Class                 fieldClass;
  private String                fieldName;
  private Map<String, Method[]> idMutators = new HashMap<String, Method[]>();

  private int         stage          = CLASSSTAGE;
  @SuppressWarnings("unchecked")
  private final Class topEntityClass;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new HydrationScanner object.
   *
   * @param  topEntityClass  DOCUMENT ME!
   * @param  entityClass     DOCUMENT ME!
   */
  @SuppressWarnings("unchecked")
  public HydrationScanner(Class topEntityClass, Class entityClass) {
    this.topEntityClass = topEntityClass;
    this.entityClass    = entityClass;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, HydrationItemDescriptor> getCacheMutators() {
    return cacheMutators;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCacheRegion() {
    return cacheRegion;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Method[]> getIdMutators() {
    return idMutators;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  public void init() {
    try {
      InputStream in = HydrationScanner.class.getClassLoader().getResourceAsStream(topEntityClass.getName().replace('.',
            '/') + ".class");
      new ClassReader(in).accept(this, ClassReader.SKIP_DEBUG);
      in = HydrationScanner.class.getClassLoader().getResourceAsStream(entityClass.getName().replace('.', '/')
          + ".class");
      new ClassReader(in).accept(this, ClassReader.SKIP_DEBUG);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  // AnnotationVisitor
  /**
   * @see  org.objectweb.asm.AnnotationVisitor#visit(java.lang.String, java.lang.Object)
   */
  @Override public void visit(String arg0, Object arg1) {
    if (Cache.class.getName().equals(annotation) && "region".equals(arg0)) {
      cacheRegion = (String) arg1;
    }

    if (Hydrated.class.getName().equals(annotation) && "factoryMethod".equals(arg0)) {
      HydrationItemDescriptor itemDescriptor = new HydrationItemDescriptor();
      itemDescriptor.setFactoryMethod((String) arg1);
      itemDescriptor.setMutators(retrieveMutators());
      cacheMutators.put(fieldName, itemDescriptor);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  // ClassVisitor
  /**
   * @see  org.objectweb.asm.ClassVisitor#visit(int, int, java.lang.String, java.lang.String, java.lang.String,
   *       java.lang.String[])
   */
  @Override public void visit(int arg0, int arg1, String arg2, String arg3, String arg4, String[] arg5) {
    try {
      clazz = Class.forName(arg2.replaceAll("/", "."));
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    stage = CLASSSTAGE;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  // Common
  /**
   * @see  org.objectweb.asm.FieldVisitor#visitAnnotation(java.lang.String, boolean)
   */
  @Override public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
    Type annotationType = Type.getType(arg0);

    switch (stage) {
      case CLASSSTAGE: {
        if (annotationType.getClassName().equals(Cache.class.getName())) {
          annotation = Cache.class.getName();
        }

        break;
      }

      case FIELDSTAGE: {
        if (annotationType.getClassName().equals(Id.class.getName())) {
          idMutators.put(fieldName, retrieveMutators());
        }

        if (annotationType.getClassName().equals(Hydrated.class.getName())) {
          annotation = Hydrated.class.getName();
        }

        break;
      }

      default: {
        annotation = null;
        fieldName  = null;

        break;
      }
    } // end switch

    return this;
  } // end method visitAnnotation

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.AnnotationVisitor#visitAnnotation(java.lang.String, java.lang.String)
   */
  @Override public AnnotationVisitor visitAnnotation(String arg0, String arg1) {
    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.AnnotationVisitor#visitArray(java.lang.String)
   */
  @Override public AnnotationVisitor visitArray(String arg0) {
    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  // FieldVisitor
  /**
   * @see  org.objectweb.asm.FieldVisitor#visitAttribute(org.objectweb.asm.Attribute)
   */
  @Override public void visitAttribute(Attribute arg0) {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.AnnotationVisitor#visitEnd()
   */
  @Override public void visitEnd() {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.AnnotationVisitor#visitEnum(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override public void visitEnum(String arg0, String arg1, String arg2) {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitField(int, java.lang.String, java.lang.String, java.lang.String,
   *       java.lang.Object)
   */
  @Override public FieldVisitor visitField(int arg0, String arg1, String arg2, String arg3, Object arg4) {
    stage     = FIELDSTAGE;
    fieldName = arg1;

    Type fieldType = Type.getType(arg2);

    switch (fieldType.getSort()) {
      case Type.BOOLEAN: {
        fieldClass = boolean.class;

        break;
      }

      case Type.BYTE: {
        fieldClass = byte.class;

        break;
      }

      case Type.CHAR: {
        fieldClass = char.class;

        break;
      }

      case Type.DOUBLE: {
        fieldClass = double.class;

        break;
      }

      case Type.FLOAT: {
        fieldClass = float.class;

        break;
      }

      case Type.INT: {
        fieldClass = int.class;

        break;
      }

      case Type.LONG: {
        fieldClass = long.class;

        break;
      }

      case Type.SHORT: {
        fieldClass = short.class;

        break;
      }

      case Type.OBJECT: {
        try {
          fieldClass = Class.forName(Type.getType(arg2).getClassName());
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        }

        break;
      }
    } // end switch

    return this;
  } // end method visitField

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitInnerClass(java.lang.String, java.lang.String, java.lang.String, int)
   */
  @Override public void visitInnerClass(String arg0, String arg1, String arg2, int arg3) {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitMethod(int, java.lang.String, java.lang.String, java.lang.String,
   *       java.lang.String[])
   */
  @Override public MethodVisitor visitMethod(int arg0, String arg1, String arg2, String arg3, String[] arg4) {
    return new EmptyVisitor();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitOuterClass(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override public void visitOuterClass(String arg0, String arg1, String arg2) {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitSource(java.lang.String, java.lang.String)
   */
  @Override public void visitSource(String arg0, String arg1) {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private Method[] retrieveMutators() {
    String mutatorName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
    Method getMethod   = null;

    try {
      getMethod = clazz.getMethod("get" + mutatorName, new Class[] {});
    } catch (Exception e) {
      // do nothing
    }

    if (getMethod == null) {
      try {
        getMethod = clazz.getMethod("is" + mutatorName, new Class[] {});
      } catch (Exception e) {
        // do nothing
      }
    }

    if (getMethod == null) {
      try {
        getMethod = clazz.getMethod(fieldName, new Class[] {});
      } catch (Exception e) {
        // do nothing
      }
    }

    Method setMethod = null;

    try {
      setMethod = clazz.getMethod("set" + mutatorName, new Class[] { fieldClass });
    } catch (Exception e) {
      // do nothing
    }

    if ((getMethod == null) || (setMethod == null)) {
      throw new RuntimeException("Unable to find a getter and setter method for the AdminPresentation field: "
        + fieldName + ". Make sure you have a getter method entitled: get" + mutatorName + "(), or is" + mutatorName
        + "(), or " + fieldName + "(). Make sure you have a setter method entitled: set" + mutatorName + "(..).");
    }

    return new Method[] { getMethod, setMethod };
  } // end method retrieveMutators

} // end class HydrationScanner
