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
//  Copyright 2015-20xx Christoph Zengler                                //
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

package org.logicng.io.graphical;

import static org.assertj.core.api.Assertions.assertThat;
import static org.logicng.io.graphical.GraphicalColor.BLACK;
import static org.logicng.io.graphical.GraphicalEdgeStyle.NO_EDGE_STYLE;
import static org.logicng.io.graphical.GraphicalNodeStyle.NO_NODE_STYLE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphicalEdgeTest {

    private final GraphicalNode n1 = new GraphicalNode("id1", "ID 1", NO_NODE_STYLE);
    private final GraphicalNode n2 = new GraphicalNode("id2", "ID 2", NO_NODE_STYLE);
    private GraphicalRepresentation grUndirected;
    private GraphicalRepresentation grDirected;

    @BeforeEach
    public void init() {
        this.grDirected = new GraphicalRepresentation(false, true);
        this.grUndirected = new GraphicalRepresentation(false, false);
        this.grDirected.addNode(this.n1);
        this.grDirected.addNode(this.n2);
        this.grUndirected.addNode(this.n1);
        this.grUndirected.addNode(this.n2);
    }

    @Test
    public void testNoStyle() {
        final GraphicalEdge edge = new GraphicalEdge(this.n1, this.n2, NO_EDGE_STYLE);
        this.grDirected.addEdge(edge);
        this.grUndirected.addEdge(edge);

        assertThat(this.grDirected.getDotString()).contains("id1 -> id2");
        assertThat(this.grDirected.getMermaidString()).contains("id1 --> id2");
        assertThat(this.grDirected.getMermaidString()).doesNotContain("linkStyle");

        assertThat(this.grUndirected.getDotString()).contains("id1 -- id2");
        assertThat(this.grUndirected.getMermaidString()).contains("id1 --- id2");
        assertThat(this.grUndirected.getMermaidString()).doesNotContain("linkStyle");
    }

    @Test
    public void testOnlyEdgeType() {
        final GraphicalEdge edge = new GraphicalEdge(this.n1, this.n2, new GraphicalEdgeStyle(GraphicalEdgeStyle.EdgeType.DOTTED, null));
        this.grDirected.addEdge(edge);
        this.grUndirected.addEdge(edge);

        assertThat(this.grDirected.getDotString()).contains("id1 -> id2 [style=dotted]");
        assertThat(this.grDirected.getMermaidString()).contains("id1 --> id2");
        assertThat(this.grDirected.getMermaidString()).contains("linkStyle 0 stroke-width:2,stroke-dasharray:3");

        assertThat(this.grUndirected.getDotString()).contains("id1 -- id2 [style=dotted]");
        assertThat(this.grUndirected.getMermaidString()).contains("id1 --- id2");
        assertThat(this.grUndirected.getMermaidString()).contains("linkStyle 0 stroke-width:2,stroke-dasharray:3");
    }

    @Test
    public void testOnlyColor() {
        final GraphicalEdge edge = new GraphicalEdge(this.n1, this.n2, new GraphicalEdgeStyle(null, BLACK));
        this.grDirected.addEdge(edge);
        this.grUndirected.addEdge(edge);

        assertThat(this.grDirected.getDotString()).contains("id1 -> id2 [color=\"#000000\", fontcolor=\"#000000\"]");
        assertThat(this.grDirected.getMermaidString()).contains("id1 --> id2");
        assertThat(this.grDirected.getMermaidString()).contains("linkStyle 0 stroke:#000000");

        assertThat(this.grUndirected.getDotString()).contains("id1 -- id2 [color=\"#000000\", fontcolor=\"#000000\"]");
        assertThat(this.grUndirected.getMermaidString()).contains("id1 --- id2");
        assertThat(this.grUndirected.getMermaidString()).contains("linkStyle 0 stroke:#000000");
    }

    @Test
    public void testAll() {
        final GraphicalEdge edge = new GraphicalEdge(this.n1, this.n2, new GraphicalEdgeStyle(GraphicalEdgeStyle.EdgeType.BOLD, BLACK));
        this.grDirected.addEdge(edge);
        this.grUndirected.addEdge(edge);

        assertThat(this.grDirected.getDotString()).contains("id1 -> id2 [color=\"#000000\", fontcolor=\"#000000\", style=bold]");
        assertThat(this.grDirected.getMermaidString()).contains("id1 --> id2");
        assertThat(this.grDirected.getMermaidString()).contains("linkStyle 0 stroke:#000000,stroke-width:4");

        assertThat(this.grUndirected.getDotString()).contains("id1 -- id2 [color=\"#000000\", fontcolor=\"#000000\", style=bold]");
        assertThat(this.grUndirected.getMermaidString()).contains("id1 --- id2");
        assertThat(this.grUndirected.getMermaidString()).contains("linkStyle 0 stroke:#000000,stroke-width:4");
        System.out.println(this.grUndirected.getMermaidString());
    }
}
