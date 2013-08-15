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

package org.broadleafcommerce.common.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;


/**
 * Convenience methods for interacting with maps.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class BLCMapUtils {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Given a collection of values and a TypedClosure that maps an appropriate key for a given value, returns a HashMap
   * of the key to a list of values that map to that key.
   *
   * @param   <K>      DOCUMENT ME!
   * @param   <CV>     DOCUMENT ME!
   * @param   <V>      DOCUMENT ME!
   *
   * @see     #keyedMap(Iterable, org.broadleafcommerce.common.util.TypedClosure)
   *
   *          <p>List<V> --> Map<K, List<V>></p>
   *
   * @param   values   DOCUMENT ME!
   * @param   closure  DOCUMENT ME!
   *
   * @return  the map
   */
  public static <K, CV extends Iterable<V>, V> Map<K, List<V>> keyedListMap(CV values, TypedClosure<K, V> closure) {
    Map<K, List<V>> map = new HashMap<K, List<V>>();

    for (V value : values) {
      K       key  = closure.getKey(value);
      List<V> list = map.get(key);

      if (list == null) {
        list = new ArrayList<V>();
        map.put(key, list);
      }

      list.add(value);
    }

    return map;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Given a collection of values and a TypedClosure that maps an appropriate key for a given value, returns a HashMap
   * of the key to the value. <b>Note: If two values share the same key, the later one will override the previous one in
   * the returned map</b>
   *
   * @param   <K>      DOCUMENT ME!
   * @param   <CV>     DOCUMENT ME!
   * @param   <V>      DOCUMENT ME!
   *
   * @see     #keyedListMap(Iterable, org.broadleafcommerce.common.util.TypedClosure)
   *
   *          <p>List<V> --> Map<K, V></p>
   *
   * @param   values   DOCUMENT ME!
   * @param   closure  DOCUMENT ME!
   *
   * @return  the map
   */
  public static <K, CV extends Iterable<V>, V> Map<K, V> keyedMap(CV values, TypedClosure<K, V> closure) {
    Map<K, V> map = new HashMap<K, V>();

    for (V value : values) {
      K key = closure.getKey(value);
      map.put(key, value);
    }

    return map;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Given an array of values and a TypedClosure that maps an appropriate key for a given value, returns a HashMap of
   * the key to the value. <b>Note: If two values share the same key, the later one will override the previous one in
   * the returned map</b>
   *
   * @param   <K>      DOCUMENT ME!
   * @param   <V>      DOCUMENT ME!
   *
   * @see     #keyedListMap(Iterable, org.broadleafcommerce.common.util.TypedClosure)
   *
   *          <p>V[] --> Map<K, V></p>
   *
   * @param   values   DOCUMENT ME!
   * @param   closure  DOCUMENT ME!
   *
   * @return  the map
   */
  public static <K, V> Map<K, V> keyedMap(V[] values, TypedClosure<K, V> closure) {
    Map<K, V> map = new HashMap<K, V>();

    if (values != null) {
      for (V value : values) {
        K key = closure.getKey(value);
        map.put(key, value);
      }
    }

    return map;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   <K>         DOCUMENT ME!
   * @param   <V>         DOCUMENT ME!
   * @param   map         DOCUMENT ME!
   * @param   comparator  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static <K, V> Map<K, V> valueSortedMap(Map<K, V> map, Comparator<Entry<K, V>> comparator) {
    Set<Entry<K, V>> valueSortedEntries = new TreeSet<Entry<K, V>>(comparator);

    for (Entry<K, V> entry : map.entrySet()) {
      valueSortedEntries.add(entry);
    }

    Map<K, V> sortedMap = new LinkedHashMap<K, V>(map.size());

    for (Entry<K, V> entry : valueSortedEntries) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }

    return sortedMap;
  }

} // end class BLCMapUtils
