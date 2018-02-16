package org.notmysock.hive.unparse;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.IdentityHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.HiveParser;

public class ASTWalker {

  StringBuilder sb = new StringBuilder();
  IdentityHashMap<ASTNode, Holder<ASTNode>> singletons = new IdentityHashMap<ASTNode, ASTWalker.Holder<ASTNode>>();

  private static final class Holder<T> {
    public final T node;
    public boolean visited = false;

    public Holder(T node) {
      this.node = node;
    }
  }

  private Holder<ASTNode> hold(ASTNode node) {
    if (!singletons.containsKey(node)) {
      singletons.put(node, new Holder<ASTNode>(node));
    }
    return singletons.get(node);
  }

  public void walk(ASTNode root) {
    Deque<Holder<ASTNode>> stack = new ArrayDeque<Holder<ASTNode>>();
    stack.push(hold(root));
    int depth = 0;

    while (!stack.isEmpty()) {
      Holder<ASTNode> next = stack.peek();

      if (!next.visited) {
        visit(next.node, depth);
        if (next.node.getChildren() != null) {
          ArrayList<Node> children = (ArrayList<Node>) next.node.getChildren().clone();
          Collections.reverse(children);
          for (Node child : children) {
            stack.push(hold((ASTNode)child));
          }
        }

        depth++;
        next.visited = true;
      } else {
        depth--;
        next.visited = false;
        stack.pop();
      }
    }
  }

  protected void visit(ASTNode node, int depth) {
    //for (int i = 0; i < depth; i++) System.out.print("  ");
    //System.out.println(node + " -> " + node.getType());
    final String s;
    switch(node.getType()) {
    case HiveParser.TOK_SELECT:
      s = "SELECT";
      break;
    case HiveParser.TOK_JOIN:
      s = "JOIN";
      break;
    case HiveParser.TOK_ORDERBY:
      s = "ORDER BY";
      break;
    case HiveParser.Identifier:
       s = node.toString();
       break;
    default: 
      s="";
    }
    System.out.print(s + " ");
  }
}
