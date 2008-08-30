package com.lubq.lm.bestwiz.order.builder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cn.bestwiz.jhf.core.dao.util.JdbcConnectionPool;

public class OrderBuilderDao {

	public static int deleteOrder(String likePatten) {

		Connection conn = null;
		PreparedStatement psmt = null;
		StringBuffer SQL = new StringBuffer(200);
		int deleted = 0;

		SQL		.append(" DELETE FROM JHF_ALIVE_ORDER ")
				.append(" WHERE   ORDER_ID LIKE  ? ");

		try {
			conn = JdbcConnectionPool.mainConnection();
			conn.setAutoCommit(false);
			conn.setReadOnly(false);
			
			psmt = conn.prepareStatement(SQL.toString());
			psmt.setString(1, "%"+likePatten+"%");
			
			deleted = psmt.executeUpdate();

			conn.commit();
			
		} catch (SQLException e) {
			/* excepiton rollback */
			if (null != conn) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					System.out.println(" error when roll back !");
				}
			}

		} finally {
			/* close jdbc operators */
			try {
				if (null != psmt) {
					psmt.close();
					psmt = null;
				}
				if (null != conn) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				System.out.println(" error  when psmt close or conn close .");
			}
		}


		return deleted;

	}
	
	public static void main(String[] args) {
		OrderBuilderDao d = new OrderBuilderDao();
		int end = d.deleteOrder("LUBQ");
		System.out.println("order delete :"+end);
	}
}
