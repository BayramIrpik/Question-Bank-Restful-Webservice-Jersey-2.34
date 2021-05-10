package com.restful.questionbank.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.restful.questionbank.model.Question;

public class Database {

	static final String DB_URL = "jdbc:mysql://localhost:3306/questionbank";
	static final String USER = "root";
	static final String PASS = "1234";

	public List<Question> getAllQuestions() {
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		List<Question> listQuestions = new ArrayList<Question>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM questions");

			while (rs.next()) {

				Question question = new Question(rs.getLong("id"), rs.getString("question_text"),
						rs.getString("option_a"), rs.getString("option_b"), rs.getString("option_c"),
						rs.getString("option_d"), rs.getString("correct_option"));

				listQuestions.add(question);
			}
		} catch (SQLException | ClassNotFoundException e) {

			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return listQuestions;
	}

	public void updateQuestion(Question question) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String query = "update questions set question_text = ?,option_a= ?,option_b=?,option_c=? "
					+ ", option_d=?,correct_option=? where id = ?";
			ps = conn.prepareStatement(query);

			ps.setString(1, question.getQuestionText());
			ps.setString(2, question.getOptionA());
			ps.setString(3, question.getOptionB());
			ps.setString(4, question.getOptionC());
			ps.setString(5, question.getOptionD());
			ps.setString(6, question.getCorrectOption());
			ps.setLong(7, question.getId());

			ps.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {

			e.printStackTrace();

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public Question addQuestion(Question question) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		Question lastQuestion=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String query = " insert into questions (question_text, option_a, option_b, option_c, option_d,correct_option)"
					+ " values (?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(query);

			ps.setString(1, question.getQuestionText());
			ps.setString(2, question.getOptionA());
			ps.setString(3, question.getOptionB());
			ps.setString(4, question.getOptionC());
			ps.setString(5, question.getOptionD());
			ps.setString(6, question.getCorrectOption());

			ps.execute();
			
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM questions where Id=(SELECT LAST_INSERT_ID())");

			while (rs.next()) {

				lastQuestion = new Question(rs.getLong("id"), rs.getString("question_text"),
						rs.getString("option_a"), rs.getString("option_b"), rs.getString("option_c"),
						rs.getString("option_d"), rs.getString("correct_option"));
			}

		} catch (SQLException | ClassNotFoundException e) {

			e.printStackTrace();

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lastQuestion;
	}

	public void deleteQuestion(long id) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String query = " delete from questions where id= ?";
			ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			ps.execute();

		} catch (SQLException | ClassNotFoundException e) {

			e.printStackTrace();

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Question getQuestionById(long id) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Question question = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String query = "select * from questions where id= ?";

			ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {

				question = new Question(rs.getLong("id"), rs.getString("question_text"), rs.getString("option_a"),
						rs.getString("option_b"), rs.getString("option_c"), rs.getString("option_d"),
						rs.getString("correct_option"));
			}
		} catch (SQLException | ClassNotFoundException e) {

			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return question;
	}
}
