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

package org.broadleafcommerce.core.catalog.dao;


import java.util.List;

import javax.annotation.Nonnull;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.Product;


/**
 * {@code CategoryDao} provides persistence access to {@code Category} instances.
 *
 * @see      org.broadleafcommerce.core.catalog.domain.Category
 * @see      org.broadleafcommerce.core.catalog.domain.Product
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public interface CategoryDao {
  /**
   * Retrieve a {@code Category} instance by its primary key.
   *
   * @param   categoryId  the primary key of the {@code Category}
   *
   * @return  the {@code Category} at the specified primary key
   */
  @Nonnull Category readCategoryById(@Nonnull Long categoryId);

  /**
   * Retrieve a {@code Category} instance by its name.
   *
   * <p>Broadleaf allows more than one category to have the same name. Calling this method could produce an exception in
   * such situations. Use {@link #readCategoriesByName(String)} instead.</p>
   *
   * @param   categoryName  the name of the category
   *
   * @return  the Category having the specified name
   */
  @Deprecated @Nonnull Category readCategoryByName(@Nonnull String categoryName);

  /**
   * A list of all categories that do not have a defaultParentCategory set.
   *
   * @return  a list of all categories that do not have a defaultParentCategory set
   */
  List<Category> readAllParentCategories();

  /**
   * Retrieve a list of {@code Category} instances by name.
   *
   * @param   categoryName  the name to search by
   *
   * @return  the Category instances having the specified name
   */
  @Nonnull List<Category> readCategoriesByName(@Nonnull String categoryName);

  /**
   * DOCUMENT ME!
   *
   * @param   categoryName  DOCUMENT ME!
   * @param   limit         DOCUMENT ME!
   * @param   offset        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @Nonnull List<Category> readCategoriesByName(@Nonnull String categoryName, int limit, int offset);

  /**
   * Persist a {@code Category} instance to the datastore.
   *
   * @param   category  the {@code Category} instance
   *
   * @return  the updated state of the passed in {@code Category} after being persisted
   */
  @Nonnull Category save(@Nonnull Category category);

  /**
   * Retrieve all categories in the datastore.
   *
   * @return  a list of all the {@code Category} instances in the datastore
   */
  @Nonnull List<Category> readAllCategories();

  /**
   * Retrieve a subset of all categories.
   *
   * @param   limit   the maximum number of results, defaults to 20
   * @param   offset  the starting point in the record set, defaults to 0
   *
   * @return  retrieve a subset of all categories.
   */
  @Nonnull List<Category> readAllCategories(@Nonnull int limit, @Nonnull int offset);

  /**
   * Retrieve all products in the datastore.
   *
   * @return  a list of all {@code Category} instances in the datastore, regardless of their category association
   */
  @Nonnull List<Product> readAllProducts();

  /**
   * DOCUMENT ME!
   *
   * @param   limit   DOCUMENT ME!
   * @param   offset  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @Nonnull List<Product> readAllProducts(@Nonnull int limit, @Nonnull int offset);

  /**
   * Retrieve a list of all child categories of the passed in {@code Category} instance.
   *
   * @param   category  the parent category
   *
   * @return  a list of all child categories
   */
  @Nonnull List<Category> readAllSubCategories(@Nonnull Category category);

  /**
   * Retrieve a list of all child categories of the passed in {@code Category} instance.
   *
   * @param   category  the parent category
   * @param   limit     the maximum number of results to return
   * @param   offset    the starting point in the record set
   *
   * @return  a list of all child categories
   */
  @Nonnull List<Category> readAllSubCategories(@Nonnull Category category, @Nonnull int limit, @Nonnull int offset);

  /**
   * Removed the passed in {@code Category} instance from the datastore.
   *
   * @param  category  the {@code Category} instance to remove
   */
  void delete(@Nonnull Category category);

  /**
   * Create a new {@code Category} instance. The system will use the configuration in
   * {@code /BroadleafCommerce/core/BroadleafCommerceFramework/src/main/resources/bl-framework-applicationContext-entity.xml}
   * to determine which polymorphic version of {@code Category} to instantiate. To make Broadleaf instantiate your
   * extension of {@code Category} by default, include an entity configuration bean in your application context xml
   * similar to:
   *
   * <p>
   * {@code <bean id="blEntityConfiguration" class="org.broadleafcommerce.common.persistence.EntityConfiguration"> <property name="entityContexts"> <list> <value>classpath:myCompany-applicationContext-entity.xml</value> </list> </property> </bean>}
   * </p>
   *
   * <p>Declare the same key for your desired entity in your entity xml that is used in the Broadleaf entity xml, but
   * change the value to the fully qualified classname of your entity extension.</p>
   *
   * @return  a {@code Category} instance based on the Broadleaf entity configuration.
   */
  @Nonnull Category create();

  /**
   * Retrieve a list of all active child categories of the passed in {@code Category} instance. This method bases its
   * search on a current time value. To make the retrieval of values more efficient, the current time is cached for a
   * configurable amount of time. See {@link #getCurrentDateResolution()}
   *
   * @param   category  the parent category
   *
   * @return  a list of all active child categories
   */
  @Nonnull List<Category> readActiveSubCategoriesByCategory(Category category);

  /**
   * Retrieve a list of all active child categories of the passed in {@code Category} instance. This method bases its
   * search on a current time value. To make the retrieval of values more efficient, the current time is cached for a
   * configurable amount of time. See {@link #getCurrentDateResolution()}
   *
   * @param   category  the parent category
   * @param   limit     the maximum number of results to return
   * @param   offset    the starting point in the record set
   *
   * @return  a list of all active child categories
   */
  @Nonnull List<Category> readActiveSubCategoriesByCategory(@Nonnull Category category, @Nonnull int limit,
    @Nonnull int offset);

  /**
   * DOCUMENT ME!
   *
   * @param   uri  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @Nonnull Category findCategoryByURI(String uri);

} // end interface CategoryDao
