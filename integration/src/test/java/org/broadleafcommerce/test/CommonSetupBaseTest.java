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

package org.broadleafcommerce.test;

import java.math.BigDecimal;

import java.util.Calendar;

import javax.annotation.Resource;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryImpl;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductBundle;
import org.broadleafcommerce.core.catalog.domain.ProductImpl;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuBundleItem;
import org.broadleafcommerce.core.catalog.domain.SkuBundleItemImpl;
import org.broadleafcommerce.core.catalog.domain.SkuImpl;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.broadleafcommerce.core.catalog.service.type.ProductType;
import org.broadleafcommerce.core.order.dao.OrderDao;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderImpl;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.core.pricing.domain.ShippingRate;
import org.broadleafcommerce.core.pricing.domain.ShippingRateImpl;
import org.broadleafcommerce.core.pricing.service.ShippingRateService;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.CountryImpl;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerAddress;
import org.broadleafcommerce.profile.core.domain.CustomerAddressImpl;
import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.core.domain.StateImpl;
import org.broadleafcommerce.profile.core.service.CountryService;
import org.broadleafcommerce.profile.core.service.CustomerAddressService;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.core.service.StateService;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public abstract class CommonSetupBaseTest extends BaseTest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource protected CatalogService catalogService;

  /** DOCUMENT ME! */
  @Resource protected CountryService countryService;

  /** DOCUMENT ME! */
  @Resource protected CustomerAddressService customerAddressService;

  /** DOCUMENT ME! */
  @Resource protected CustomerService customerService;

  /** DOCUMENT ME! */
  @Resource(name = "blOrderService")
  protected OrderService orderService;

  /** DOCUMENT ME! */
  @Resource protected ShippingRateService shippingRateService;

  /** DOCUMENT ME! */
  @Resource protected StateService stateService;

  @Resource private OrderDao orderDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------


  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ProductBundle addProductBundle() {
    // Create the product
    Product p = addTestProduct("bundleproduct1", "bundlecat1");

    // Create the sku for the ProductBundle object
    Sku bundleSku = catalogService.createSku();
    bundleSku.setName(p.getName());
    bundleSku.setRetailPrice(new Money(44.99));
    bundleSku.setActiveStartDate(p.getActiveStartDate());
    bundleSku.setActiveEndDate(p.getActiveEndDate());
    bundleSku.setDiscountable(true);

    // Create the ProductBundle and associate the sku
    ProductBundle bundle = (ProductBundle) catalogService.createProduct(ProductType.BUNDLE);
    bundle.setDefaultCategory(p.getDefaultCategory());
    bundle.setDefaultSku(bundleSku);
    bundle = (ProductBundle) catalogService.saveProduct(bundle);

    // Reverse-associate the ProductBundle to the sku (Must be done this way because it's a
    // bidirectional OneToOne relationship
    // bundleSku.setDefaultProduct(bundle);
    // catalogService.saveSku(bundleSku);

    // Wrap the product/sku that is part of the bundle in a SkuBundleItem
    SkuBundleItem skuBundleItem = new SkuBundleItemImpl();
    skuBundleItem.setBundle(bundle);
    skuBundleItem.setQuantity(1);
    skuBundleItem.setSku(p.getDefaultSku());

    // Add the SkuBundleItem to the ProductBundle
    bundle.getSkuBundleItems().add(skuBundleItem);
    bundle = (ProductBundle) catalogService.saveProduct(bundle);

    return bundle;
  } // end method addProductBundle

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   productName   DOCUMENT ME!
   * @param   categoryName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Product addTestProduct(String productName, String categoryName) {
    return addTestProduct(productName, categoryName, true);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   productName   DOCUMENT ME!
   * @param   categoryName  DOCUMENT ME!
   * @param   active        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Product addTestProduct(String productName, String categoryName, boolean active) {
    Calendar activeStartCal = Calendar.getInstance();
    activeStartCal.add(Calendar.DAY_OF_YEAR, -2);

    Calendar activeEndCal = Calendar.getInstance();
    activeEndCal.add(Calendar.DAY_OF_YEAR, -1);

    Category category = new CategoryImpl();
    category.setName(categoryName);
    category.setActiveStartDate(activeStartCal.getTime());
    category = catalogService.saveCategory(category);

    Sku newSku = new SkuImpl();
    newSku.setName(productName);
    newSku.setRetailPrice(new Money(44.99));
    newSku.setActiveStartDate(activeStartCal.getTime());

    if (!active) {
      newSku.setActiveEndDate(activeEndCal.getTime());
    }

    newSku.setDiscountable(true);
    newSku = catalogService.saveSku(newSku);

    Product newProduct = new ProductImpl();
    newProduct.setDefaultCategory(category);
    newProduct.setDefaultSku(newSku);
    newProduct = catalogService.saveProduct(newProduct);

    return newProduct;
  } // end method addTestProduct

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * DOCUMENT ME!
   */
  public void createCountry() {
    Country country = new CountryImpl();
    country.setAbbreviation("US");
    country.setName("United States");
    countryService.save(country);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Customer createCustomer() {
    Customer customer = customerService.createCustomerFromId(null);

    return customer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a country, state, and customer with the supplied customerAddress.
   *
   * @param   customerAddress  DOCUMENT ME!
   *
   * @return  customer created
   */
  public CustomerAddress createCustomerWithAddress(CustomerAddress customerAddress) {
    createCountry();
    createState();

    Customer customer = createCustomer();
    customer.setUsername(String.valueOf(customer.getId()));
    customerAddress.setCustomer(customer);

    return saveCustomerAddress(customerAddress);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a country, state, and customer with some CustomerAddresses.
   *
   * @return  customer created
   */
  public Customer createCustomerWithAddresses() {
    createCountry();
    createState();

    CustomerAddress ca1      = new CustomerAddressImpl();
    Address         address1 = new AddressImpl();
    address1.setAddressLine1("1234 Merit Drive");
    address1.setCity("Bozeman");
    address1.setPostalCode("75251");
    ca1.setAddress(address1);
    ca1.setAddressName("address1");

    CustomerAddress caResult = createCustomerWithAddress(ca1);
    assert caResult != null;
    assert caResult.getCustomer() != null;

    Customer customer = caResult.getCustomer();

    CustomerAddress ca2      = new CustomerAddressImpl();
    Address         address2 = new AddressImpl();
    address2.setAddressLine1("12 Testing Drive");
    address2.setCity("Portland");
    address2.setPostalCode("75251");
    ca2.setAddress(address2);
    ca2.setAddressName("address2");
    ca2.setCustomer(customer);

    CustomerAddress addResult = saveCustomerAddress(ca2);
    assert addResult != null;

    return customer;
  } // end method createCustomerWithAddresses

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Create a state, country, and customer with a basic order and some addresses.
   *
   * @return  create a state, country, and customer with a basic order and some addresses.
   */
  public Customer createCustomerWithBasicOrderAndAddresses() {
    Customer customer = createCustomerWithAddresses();
    Order    order    = new OrderImpl();
    order.setStatus(OrderStatus.IN_PROCESS);
    order.setTotal(new Money(BigDecimal.valueOf(1000)));

    assert order.getId() == null;
    order.setCustomer(customer);
    order = orderDao.save(order);
    assert order.getId() != null;

    return customer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * DOCUMENT ME!
   */
  public void createShippingRates() {
    ShippingRate sr = new ShippingRateImpl();
    sr.setFeeType("SHIPPING");
    sr.setFeeSubType("ALL");
    sr.setFeeBand(1);
    sr.setBandUnitQuantity(BigDecimal.valueOf(29.99));
    sr.setBandResultQuantity(BigDecimal.valueOf(8.5));
    sr.setBandResultPercent(0);

    ShippingRate sr2 = new ShippingRateImpl();

    sr2.setFeeType("SHIPPING");
    sr2.setFeeSubType("ALL");
    sr2.setFeeBand(2);
    sr2.setBandUnitQuantity(BigDecimal.valueOf(999999.99));
    sr2.setBandResultQuantity(BigDecimal.valueOf(8.5));
    sr2.setBandResultPercent(0);

    shippingRateService.save(sr);
    shippingRateService.save(sr2);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  public void createState() {
    State state = new StateImpl();
    state.setAbbreviation("KY");
    state.setName("Kentucky");
    state.setCountry(countryService.findCountryByAbbreviation("US"));
    stateService.save(state);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Saves a customerAddress with state KY and country US. Requires that createCountry() and createState() have been
   * called
   *
   * @param   customerAddress  DOCUMENT ME!
   *
   * @return  saves a customerAddress with state KY and country US.
   */
  public CustomerAddress saveCustomerAddress(CustomerAddress customerAddress) {
    State state = stateService.findStateByAbbreviation("KY");
    customerAddress.getAddress().setState(state);

    Country country = countryService.findCountryByAbbreviation("US");
    customerAddress.getAddress().setCountry(country);

    return customerAddressService.saveCustomerAddress(customerAddress);
  }

} // end class CommonSetupBaseTest
