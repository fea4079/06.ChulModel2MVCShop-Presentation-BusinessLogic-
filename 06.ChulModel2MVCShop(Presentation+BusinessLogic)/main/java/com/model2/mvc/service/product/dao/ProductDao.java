package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;


/**
 * Servlet implementation class ProductDAO
 */

public class ProductDao{
	
	public ProductDao() {
		
	}
	
	public Product findProduct(int prodNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
//		System.out.println("ProductDAO�� fineProduct�� DBUtil ���� �Ϸ�");

		String sql = " SELECT * FROM product WHERE prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();
//		System.out.println("ProductDAO: ��ǰselect���� �Է¿Ϸ�");

		Product product = null;
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
			
		}
		rs.close();
		stmt.close();
		con.close();
		System.out.println("ProductDAO.findProduct aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		return product;
	}
	
	public void insertProduct(Product product)throws Exception{
		Connection con = DBUtil.getConnection();

		String sql = "	INSERT INTO product(PROD_NO, PROD_NAME, PROD_DETAIL, MANUFACTURE_DAY, PRICE, IMAGE_FILE, REG_DATE) \r\n"
				+ "			    VALUES (seq_product_prod_no.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
//		stmt.setInt(1, productVO.getProdNo());
		stmt.setString(1, product.getProdName());
		System.out.println("product.getProdName() :"+product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
//		stmt.setDate(7, productVO.getRegDate());
		stmt.executeUpdate();
		System.out.println("ProductDAO.insertProduct bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		
		stmt.close();
		con.close();
	}
	
	
	public Map<String,Object> getProductList(Search search) throws Exception{
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product ";
		
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0")) {
				sql += " WHERE prod_no LIKE'%" + search.getSearchKeyword()
						+ "%'";
			} else if (search.getSearchCondition().equals("1")) {
				sql += " WHERE prod_name LIKE'%" + search.getSearchKeyword()
						+ "%'";
			} else if (search.getSearchCondition().equals("2")) {
				sql += " WHERE price LIKE'%" + search.getSearchKeyword()
				+ "%'";
			}
		}
		sql += " ORDER BY prod_no";

		System.out.println("ProductDao.getProductList Original SQL= " + sql);
		
		//TotalCount Get
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDao.getProductList totalCount= " + totalCount);
		
		//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		System.out.println("ProductDao.java.getProductList search= "+search);
		
		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()){
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setPrice(rs.getInt("price"));
			list.add(product);
		}
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);

		rs.close();
		stmt.close();
		con.close();
		System.out.println("ProductDao.java.getProductList return map ="+map);
		return map;
		}
		
		
	
	
	public void updateProduct(Product product) throws Exception{
		
		Connection con = DBUtil.getConnection();

		String sql = "	update PRODUCT set PROD_NAME=?, PROD_DETAIL=?, \r\n"
										+ "MANUFACTURE_DAY=?, PRICE=?, IMAGE_FILE=?, REG_DATE=? \r\n"
										+ "WHERE PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
//		stmt.setInt(1, productVO.getProdNo());
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.setDate(6, product.getRegDate());
		stmt.setInt(7, product.getProdNo());
		stmt.executeUpdate();
		System.out.println("ProductDAO.java.updateProduct dddddddddddddddddddddddddddddddddd");
		
		stmt.close();
		con.close();
	}
	
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// �Խ��� currentPage Row ��  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("ProductDAO.java  make SQL :: "+ sql);	
		System.out.println("ProductDao.java.makeCurrentPageSql return sql= "+sql+"\n");
		return sql;
	}
}
