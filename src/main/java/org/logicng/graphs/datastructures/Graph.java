///////////////////////////////////////////////////////////////////////////
//                   __                _      _   ________               //
//                  / /   ____  ____ _(_)____/ | / / ____/               //
//                 / /   / __ \/ __ `/ / ___/  |/ / / __                 //
//                / /___/ /_/ / /_/ / / /__/ /|  / /_/ /                 //
//               /_____/\____/\__, /_/\___/_/ |_/\____/                  //
//                           /____/                                      //
//                                                                       //
//               The Next Generation Logic Library                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////
//                                                                       //
//  Copyright 2015-2016 Christoph Zengler                                //
//                                                                       //
//  Licensed under the Apache License, Version 2.0 (the "License");      //
//  you may not use this file except in compliance with the License.     //
//  You may obtain a copy of the License at                              //
//                                                                       //
//  http://www.apache.org/licenses/LICENSE-2.0                           //
//                                                                       //
//  Unless required by applicable law or agreed to in writing, software  //
//  distributed under the License is distributed on an "AS IS" BASIS,    //
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or      //
//  implied.  See the License for the specific language governing        //
//  permissions and limitations under the License.                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////

package org.logicng.graphs.datastructures;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * A generic graph.
 * @version 1.2
 * @since 1.2
 */
public class Graph<T> {

  private Map<T, Node<T>> nodes;

  /**
   * Constructor for a new graph.
   */
  public Graph() {
    this.nodes = new LinkedHashMap<>();
  }

  /**
   * Returns the node with a given content. If such a node does not exist, it is created and added to the graph.
   * @param content the given node content
   * @return the node with the given content
   */
  public Node<T> node(T content) {
    final Node<T> search = nodes.get(content);
    if (search != null)
      return search;
    final Node<T> n = new Node<>(content, this);
    nodes.put(content, n);
    return n;
  }

  /**
   * Returns all nodes of the graph.
   * @return a set containing all nodes of the graph
   */
  public Set<Node<T>> nodes() {
    return new LinkedHashSet<>(nodes.values());
  }

  /**
   * Adds an edge between two given nodes, which must both belong to this graph. (Does nothing if the nodes are already connected)
   * @param o the first given node
   * @param t the second given node
   */
  public void connect(Node<T> o, Node<T> t) {
    if (!o.equals(t)) {
      o.connectTo(t);
      t.connectTo(o);
    }
  }

  /**
   * Removes the edge between two given nodes. (Does nothing if the nodes are not connected)
   * @param o the first given node
   * @param t the second given node
   */
  public void disconnect(Node<T> o, Node<T> t) {
    if (!o.equals(t)) {
      o.disconnectFrom(t);
      t.disconnectFrom(o);
    }
  }
}
