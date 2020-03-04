import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class DataAccess {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/example";
	private static final String USER = "root"; //DB ID
	private static final String PASS = "hyngsk1540"; //DB 패스워드
	DataTransfer eList;
	private Vector data = new Vector();


	public DataAccess() {

	}

	public DataAccess(DataTransfer eList) {
		this.eList = eList;
		System.out.println("DAO=>" + eList);
	}

	/**
	 * DB연결 메소드
	 */
	public Connection getConn() {
		Connection con = null;

		try {
			Class.forName(DRIVER); //1. 드라이버 로딩
			con = DriverManager.getConnection(URL, USER, PASS); //2. 드라이버 연결

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}


	/**
	 * 멤버리스트 출력
	 */
	public Vector getScheduleList() {

		//  Vector data = new Vector();  //Jtable에 값을 쉽게 넣는 방법 1. 2차원배열   2. Vector 에 vector추가
		Connection con = null;       //연결
		PreparedStatement ps = null; //명령
		ResultSet rs = null;         //결과

		try {
			con = getConn();
			String sql = "select * from schedule order by timeset asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				String timeset = rs.getString("timeset");
				String events = rs.getString("events");

				Vector row = new Vector();
				row.add(timeset);
				row.add(events);
				data.add(row);
			}//while
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(data);
		return data;
	}//getMemberList()

	//포기..나중에
	public boolean isScheduleListAt(String date) {

		Vector data = getScheduleList();
		//int dateInt = Integer.parseInt(date);

//        for (int i = 0; i < data.size(); i++) {
//            System.out.println(data.get(dateInt));
//        }
//
//        for(int i = 0; i < data.size(); i++){
//            if(data.get(i) == date){
//                System.out.println(date);
//                return true;
//            }
//        }
//
//
//        System.out.println(date);
//        if(data.get(Integer.parseInt(date)) != null){
//
//            return true;
//        }

		return false;
	}
//
//    public boolean updateMember(DataTransfer vMem) {
//        System.out.println("dto=" + vMem.toString());
//        boolean ok = false;
//        Connection con = null;
//        PreparedStatement ps = null;
//        try {
//
//            con = getConn();
//            String sql = "update user set name=?, tel=?, birth=?, job=?, gender=?" +
//                    ",intro=? " + "where id=? and pwd=?";
//
//            ps = con.prepareStatement(sql);
//
//            ps.setString(1, vMem.getName());
//            ps.setString(2, vMem.getTel());
////            ps.setString(3, vMem.getAddr());
//            ps.setString(3, vMem.getBirth());
//            ps.setString(4, vMem.getJob());
//            ps.setString(5, vMem.getGender());
////            ps.setString(7, vMem.getEmail());
//            ps.setString(6, vMem.getIntro());
//            ps.setString(7, vMem.getId());
//            ps.setString(8, vMem.getPwd());
//
//            int r = ps.executeUpdate(); //실행 -> 수정
//            // 1~n: 성공 , 0 : 실패
//
//            if (r > 0) ok = true; //수정이 성공되면 ok값을 true로 변경
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return ok;
//    }

	/**
	 * 하나의 이벤트 탐색
	 */
	public boolean isAtScheduleOnMonth(String date) {
		Connection con = null;       //연결
		PreparedStatement ps = null; //명령
		ResultSet rs = null;         //결과
		try {

			con = getConn();
			String sql = "select * from schedule where timeset=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, date);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 이벤트 등록
	 */
	public boolean insertSchedule(DataTransfer dto) {

		boolean ok = false;

		Connection con = null;       //연결
		PreparedStatement ps = null; //명령

		try {

			con = getConn();
			String sql = "insert into schedule(" +
					"timeset,events) " +
					"values(?,?)";

			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getTimeset());
			ps.setString(2, dto.getEventName());
			int r = ps.executeUpdate(); //실행 -> 저장


			if (r > 0) {
				System.out.println("이벤트 등록 성공");
				ok = true;
			} else {
				System.out.println("이벤트 등록 실패");
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}//insertMmeber


	/**
	 * 회원정보 삭제 :
	 * tip: 실무에서는 회원정보를 Delete 하지 않고 탈퇴여부만 체크한다.
	 */
	public boolean deleteSchedule(String date) {

		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConn();
			String sql = "delete from schedule where timeset=? ";

			ps = con.prepareStatement(sql);
			ps.setString(1, date);
			int r = ps.executeUpdate(); // 실행 -> 삭제

			if (r > 0) ok = true; //삭제됨;

		} catch (Exception e) {
			System.out.println(e + "-> 오류발생");
		}
		return ok;
	}


	/**
	 * DB데이터 다시 불러오기
	 */
	public void ScheduleSelectAll(DefaultTableModel model) {


		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConn();
			String sql = "select * from schedule order by timeset asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			// DefaultTableModel에 있는 데이터 지우기
			for (int i = 0; i < model.getRowCount(); ) {
				model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = {rs.getString(1), rs.getString(2)};

				model.addRow(data);
			}

		} catch (SQLException e) {
			System.out.println(e + "=> userSelectAll fail");
		} finally {

			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
