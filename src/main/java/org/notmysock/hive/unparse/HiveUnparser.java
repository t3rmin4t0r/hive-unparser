package org.notmysock.hive.unparse;

import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.ParseException;
import org.apache.hadoop.hive.ql.parse.ParseUtils;

public class HiveUnparser {
  public static void main(String[] args) throws Exception {
    ASTNode parse = ParseUtils.parse(HiveQueries.q55);
    ASTWalker w = new ASTWalker();
    w.walk(parse);
  }
}
