package org.notmysock.hive.unparse;

public class HiveQueries {
  public static final String q55 = "select  i_brand_id brand_id, i_brand brand,\n" + 
      "  sum(ss_ext_sales_price) ext_price\n" + 
      " from date_dim, store_sales, item\n" + 
      " where date_dim.d_date_sk = store_sales.ss_sold_date_sk\n" + 
      "  and store_sales.ss_item_sk = item.i_item_sk\n" + 
      "  and i_manager_id=36\n" + 
      "  and d_moy=12\n" + 
      "  and d_year=2001\n" + 
      " group by i_brand, i_brand_id\n" + 
      " order by ext_price desc, i_brand_id\n" + 
      "limit 100";
}
