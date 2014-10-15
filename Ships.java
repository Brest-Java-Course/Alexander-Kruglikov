import java.io.*;
import java.util.Calendar;
import java.util.*;
import javax.swing.*;
import java.awt.*;
abstract class Ship{
	// ����� ��� ���� ����
	int[] size = new int[3]; 	// ������ size[0]-�����, size[1]-������, size[2]-������ (�) 
	double massa; 				// ����� ��� ������������� (�����)
	double fuel; 				// ������� ���������� ����(��) (�����)
	double outlayFuel; 			// ������� ������ ������� ��� �������� (����/��)
	int speed; 					// ������������ �������� 
	int[][] course; 			// ���� - ������� � ������ ������ �� ����� ����������
	int[] location = new int[2]; // ��������������� - ������� � ������
	Calendar dateStart = Calendar.getInstance(); // ����� �����������
	Calendar dateEnd = Calendar.getInstance();	 // ����� �������� � �������� �����
	boolean repair = false;
	// �����������
	Ship(int[] size,double massa,double fuel,double outlayFuel, int speed,int[][] course){
		this.massa = massa;
		this.fuel = fuel;
		this.outlayFuel = outlayFuel;
		this.speed = speed;
		for(int i=0; i<3; i++){
			this.size[i] = size[i];
		}
		this.course = new int[course.length][2];
		for(int i = 0; i<course.length; i++){
			for(int j=0; j<2; j++) {
				this.course[i][j] = course[i][j];
			}
		}
		for(int i = 0; i<2; i++) {
			this.location[i] = course[0][i];
		}
	}
	// ������
	void repair() { // ������
		if (repair == false) repair = true;
		else System.out.println("������� ������ ��������"); //??? ������������� �� ���� � ���������
	}
	void fuel(double f) { // �������� �������� - ���������� �������� ������� ��� ��������� � ���� fuel
		fuel += f; 
	}
	double outlayFuel(int[] start, int[] end) { // ������ �������
		return outlayFuel*distance(start, end)/100;
	}
	void changeCourse(int n, int[] crs) { // ��������� �����, n - � ����� �� �����, crs - ���������� ������ ������
		course[n][0] = crs[0];
		course[n][1] = crs[1];
	}
	double distance(int[] start, int[] end) { // ���������� ����������
		// ������� ������� ��������� {1� = 0.017453292519943 rad}
		double pi180 = (Math.PI)/180;
		double lat1 = start[0] * pi180;	// ������ 
		double lng1 = start[1] * pi180;	// �������
		double lat2 = end[0] * pi180; 	// ������
		double lng2 = end[1] * pi180; 	// �������
 
		double r = 6372.797; 			// ������ ����� � ��
		double dlat = (lat2 - lat1)/2; 	//������ ������
		double dlng = (lng2 - lng1)/2; 	//������ �������
	
		double a = Math.sin(dlat) * Math.sin(dlat) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlng) * Math.sin(dlng);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return r * c;
	}
	void dateEnd(int[] start, int[] end, int year, int mounth, int day, int hour, int minutes) { // ����� ��������
		dateStart.set(year, mounth, day, hour, minutes);
		dateEnd.set(year, mounth, day, hour, minutes);
		double dlnway = distance(start, end);
		int dayway = (int)(dlnway/speed);
		dateEnd.add(dateEnd.DATE, dayway);
	}
	void location(int[] l) { // ���������������
		location[0] = l[0];
		location[1] = l[1];
	}
	abstract void show(); 	// ����� ���������� � ������� 
	abstract void showfield(JTextField[] txtfield); 	// ����� ���������� � �������
}
abstract class Military extends Ship{
	int ammunition; 		// �����������
	int restammunition; 	// ������ ������������
	Military(int[] s,double m,double f,double oF, int sp,int[][] c, int ammunition){
		super(s,m,f,oF,sp,c);
		this.ammunition = ammunition;
		this.restammunition = this.ammunition; 
	}
	abstract void shooting(int n); 			// �������� -> ������ ������������
	abstract void addAmmunition(int n); 	// ���������� ������������
}
abstract class Truck extends Ship{
	double trum; 		// ������� �����
	double resttrum; 	// ������� ������� �����
	String gruz = "|"; 	// ������������ �����
	double massGruz=0; 	// ����� �����
	Truck(int[] s,double m,double f,double oF, int sp,int[][] c,double trum) {
		super(s, m, f, oF, sp, c);
		this.trum = trum;
		this.resttrum = this.trum; 
	}
	abstract void loading(double n, String t);  	// ��������
	abstract void unloading(double n, String t); 	// ��������
}
abstract class Civil extends Ship{
	int passenger; 		// ���-�� ������������ ����
	int restpassenger; 	// ���-�� ����������
	Civil(int[] s,double m,double f,double oF, int sp,int[][] c,int passenger) {
		super(s,m,f,oF, sp,c);
		this.passenger = passenger;
		restpassenger = 0;
	}
	void embarkation(int n) { 	// ������� ����������
		if (restplays() >= n) {	
			restpassenger += n;
			System.out.println("�� ���� �������: "+n+" ��������(��)");
		} else System.out.println("����� ���������,\n������� ������ ����: "+(passenger-restpassenger));
	}
	void disembarkation(int n) { // ������� ����������
		if (restpassenger >= n) {
			restpassenger -= n;
			System.out.println("�������� �� �����: "+n+" ��������(a,��)");
		} else System.out.println("����� �����,\n������� ����������:"+restpassenger);
	}
	int restplays() { // ���-�� ��������� ����
		return passenger - restpassenger;  
	}
}
interface Fishing {
	abstract void fishing(String[] v_fish); // ����� ���� -> ��� ���� (����� ���)-> 
									  		// ���������� �����
}
interface Air {
	abstract void take_off(int a); 	// �����
	abstract void landing(int a); 	// ������� �� ���������
}
// �����
class Liner extends Civil {
	int num_flow;		// ���-�� ������
	int num_helth;		// ���-�� ��� ������
	int[][] f_plays;	// ����� �� ������
	Liner(int[] s,double m,double f,double oF, int sp,int[][] c,int p, int nf, int nh) {
		super(s,m,f,oF,sp,c,p);
		num_flow = nf;
		num_helth = nh;
		f_plays = new int[num_flow][(int)(p/num_flow)];
	}
	void embarkation(int n) { // ������� ����������
		int num_p=0; // ���-�� �������� �� ���� ����������
		if ((restplays() >= n)||((restplays()<n)&(restplays()>0))) {
			for (int i=0; i<f_plays.length; i++){
				for (int j=0; j<f_plays[0].length; j++) {
					if (f_plays[i][j]==0) {
						f_plays[i][j]=1;
						num_p++;
					}
					if (num_p == n) break;
				}
				if (num_p == n) break;
			}
			restpassenger += num_p;
			System.out.println("���������: "+num_p+" ��������(��) \n������� " +
					"��������� ����: "+restplays());
		} else System.out.println("����� ���������,\n������� ������ ����:"+(passenger-restpassenger)); 
	}	
	void disembarkation(int n) { // ������� ����������
		int num_p = 0; // ���-�� ��������� �� ����� ����������
		if ((restpassenger > 0)&(num_p < n)) {
			for (int i=0; i<f_plays.length; i++) {
				for (int j=0; j<f_plays[0].length; j++) {
					if (f_plays[i][j]==1) {
						f_plays[i][j]=0;
						num_p++;
						restpassenger--;
					}
					if (num_p == n) break;
				}
				if (num_p == n) break;
			}
			System.out.println("�������� �� �����: "+num_p+" ��������(��)\n�������" +
					" ����������: "+restpassenger);
		} else System.out.println("�� ����� ��� ����������:\n ���-�� ����������:"+restpassenger+
				" ��������� ����: "+restplays());
	}
	void show() {
		String txt = "\"������\"\n����������� ��������������:\n";
		txt += "�������������: "+ massa+"\n";
		txt += "�����: "+ size[0]+"\n";
		txt += "������: "+ size[1]+"\n";
		txt += "������������ ��������: "+ speed+" �����\n";
		txt += "������������ �����: "+ num_flow+"\n";
		txt += "���� �� �����: "+ (int)(passenger/num_flow)+"\n";
		txt += "����� ������������ ����: "+ passenger+"\n";
		txt += "�� ��� ��������� ����: "+ (passenger-restpassenger)+"\n";
		txt += "��� ������: "+ num_helth+"\n";
		txt += "���� �����������: "+ dateStart.getTime()+"\n";
		txt += "���� ������������������ ��������: "+ dateEnd.getTime()+"\n";
		txt += "�� ����� �����: "+ restpassenger+" ����������";
		JOptionPane.showMessageDialog(null, txt);
	}
	void showfield(JTextField[] txtfield) {
		int[] s = new int[2];
		int[] e = new int[2];
		s[0] = course[0][0];
		s[1] = course[0][1];
		e[0] = course[1][0];
		e[1] = course[1][1];
		txtfield[0].setText(""+massa+" ����.");
		txtfield[1].setText(""+size[0]+" ������.");
		txtfield[2].setText(""+size[1]+" ������.");
		txtfield[3].setText(""+speed+" �����.");
		txtfield[4].setText(""+num_flow);
		txtfield[5].setText(""+(int)(passenger/num_flow));
		txtfield[6].setText(""+passenger);
		txtfield[7].setText(""+(passenger-restpassenger));
		txtfield[8].setText(""+num_helth);
		txtfield[9].setText(""+dateStart.getTime());
		txtfield[10].setText(""+dateEnd.getTime());
		txtfield[11].setText("�������/������ - "+course[0][0]+"/"+course[0][1]+" : "+course[1][0]+"/"+course[1][1]);
		txtfield[12].setText(""+distance(s, e));
		txtfield[13].setText(""+outlayFuel(s, e));
		txtfield[14].setText(""+restpassenger+" ����������");
	}
}
// ����
class Yacht extends Civil {
	// ��� ����: ��������, ��������, ������-��������
	String[] vid = {"��������","��������","������-��������"};
	String v_yacht;
	Yacht(int[] s,double m,double f,double oF, int sp,int[][] c,int p, int vid) {
		super(s,m,f,oF,sp,c,p);
		v_yacht = this.vid[vid];
	}
	void show() {
		
	}
	void showfield(JTextField[] txtfield) {
		
	}
}
// ������
class Tanker extends Truck {
	int q_tank = 1; 				// ���-�� ������
	double v_tank = trum/q_tank; 	// ������� �����
	Tanker(int[] s,double m,double f,double oF, int sp,int[][] c,double trum, int q_tank) {
		super(s, m, f, oF, sp, c, trum);
		this.q_tank = q_tank;
		v_tank = trum/this.q_tank;
	}
	void loading(double n, String t) { // ��������
		if (resttrum - n > 0) {
			massGruz +=n;
			resttrum -= n;
			gruz = t+"|";
			System.out.println("�������� ���������. ����:"+t+" "+massGruz+" m3");
		} else System.out.println("��� ����� ���������,\n������� ������� �����:"+resttrum+
				" m3 \n��� "+resttrum/v_tank+" �����");
	}
	void unloading(double n, String t) { // ��������
		if (massGruz - n >= 0) {
			massGruz -=n;
			resttrum +=n;
			if (resttrum == trum){
				gruz = gruz.replaceAll(t,"");
			} 
			System.out.println("��������� ���������.\n������� �����:"+t+" "+massGruz+" m3\n"+
					"������� ����� "+resttrum+" m3 \n��� "+resttrum/v_tank+" �����");
		}
	}
	void show() {
		String txt = "\"������\"\n����������� ��������������:\n";
		txt += "�������������: "+ massa+"\n";
		txt += "�����: "+ size[0]+"\n";
		txt += "������: "+ size[1]+"\n";
		txt += "������������ ��������: "+ speed+" �����\n";
		txt += "���������� ������: "+ q_tank+"\n";
		txt += "������� �����: "+ v_tank+"\n";
		txt += "������� �����: "+ trum+"\n";
		txt += "������� ������� �����: "+ resttrum+"\n";
		txt += "������������ �����: "+ gruz+"\n";
		txt += "����� �����: "+ massGruz+"\n";
		txt += "���� �����������: "+ dateStart.getTime()+"\n";
		txt += "���� ������������������ ��������: "+ dateEnd.getTime()+"\n";
		JOptionPane.showMessageDialog(null, txt);
	}
	void showfield(JTextField[] txtfield) {
		int[] s = new int[2];
		int[] e = new int[2];
		s[0] = course[0][0];
		s[1] = course[0][1];
		e[0] = course[1][0];
		e[1] = course[1][1];
		txtfield[0].setText(""+massa+" ����.");
		txtfield[1].setText(""+size[0]+" ������.");
		txtfield[2].setText(""+size[1]+" ������.");
		txtfield[3].setText(""+speed+" �����.");
		txtfield[4].setText(""+q_tank);
		txtfield[5].setText(""+v_tank);
		txtfield[6].setText(""+trum);
		txtfield[7].setText(""+resttrum);
		txtfield[8].setText(""+gruz);
		txtfield[9].setText(""+massGruz);
		txtfield[10].setText(""+dateStart.getTime());
		txtfield[11].setText(""+dateEnd.getTime());
		txtfield[12].setText("�������/������ - "+course[0][0]+"/"+course[0][1]+" : "+course[1][0]+"/"+course[1][1]);
		txtfield[13].setText(""+distance(s, e));
		txtfield[14].setText(""+outlayFuel(s, e));
	}
}
// �����
class Barge extends Truck {
	Barge(int[] s,double m,double f,double oF, int sp,int[][] c,double trum) {
		super(s, m, f, oF, sp, c, trum);
	}
	void loading(double n, String t) { // ��������
		if (resttrum - n > 0) {
			massGruz +=n;
			resttrum -= n;
			gruz += t+"|";
			System.out.println("���� "+t+" ����� "+n+" ���� ��������");
		} else System.out.println("����� ���������,\n������� ����� ��� �������:"+resttrum);
	}
	void unloading(double n, String t) { // ��������
		if (massGruz >= n) {
			trum -= n;
			massGruz -=n;
			resttrum +=n;
			gruz = gruz.replaceAll(t, "");
			System.out.println("��������� "+t+" ������ "+n+" ����");
			System.out.println("������� �����:"+t+" "+massGruz+" ����\n"+
			"������� ����� "+resttrum+" ����");
		}
	}
	void show_trum() {
		System.out.println("���� �� ���������: "+gruz+" ������ - "+massGruz+" ����");
		String txt;
		txt = "���� �� ���������: "+gruz+" ������ - "+massGruz+" ����";
		JOptionPane.showMessageDialog(null, txt);
	}
	void show() {
		String txt = "\"�����\"\n����������� ��������������:\n";
		txt += "�������������: "+ massa+"\n";
		txt += "�����: "+ size[0]+"\n";
		txt += "������: "+ size[1]+"\n";
		txt += "������������ ��������: "+ speed+" �����\n";
		txt += "������� �����: "+ trum+"\n";
		txt += "������� ������� �����: "+ resttrum+"\n";
		txt += "������������ �����: "+ gruz+"\n";
		txt += "����� �����: "+ massGruz+"\n";
		txt += "���� �����������: "+ dateStart.getTime()+"\n";
		txt += "���� ������������������ ��������: "+ dateEnd.getTime()+"\n";
		JOptionPane.showMessageDialog(null, txt);
	}
	void showfield(JTextField[] txtfield) {
		int[] s = new int[2];
		int[] e = new int[2];
		s[0] = course[0][0];
		s[1] = course[0][1];
		e[0] = course[1][0];
		e[1] = course[1][1];
		txtfield[0].setText(""+massa+" ����.");
		txtfield[1].setText(""+size[0]+" ������.");
		txtfield[2].setText(""+size[1]+" ������.");
		txtfield[3].setText(""+speed+" �����.");
		txtfield[4].setText(""+trum);
		txtfield[5].setText(""+resttrum);
		txtfield[6].setText(""+gruz);
		txtfield[7].setText(""+massGruz);
		txtfield[8].setText(""+dateStart.getTime());
		txtfield[9].setText(""+dateEnd.getTime());
		txtfield[10].setText("�������/������ - "+course[0][0]+"/"+course[0][1]+" : "+course[1][0]+"/"+course[1][1]);
		txtfield[11].setText(""+distance(s, e));
		txtfield[12].setText(""+outlayFuel(s, e));
	}
}
// �������
class Esminec extends Military {
	Esminec(int[] s,double m,double f,double oF, int sp,int[][] c, int am){
		super(s,m,f,oF,sp,c,am);
	}
	void shooting(int n) { // �������� -> ������ ������������
		if (restammunition - n < 0) {
			System.out.println("�������� ����������,\n������������ �����������"+restammunition);
		} else {
			restammunition -= n;
			System.out.println("�������� ������������.\n�������������-"+n+" ������\n" +
					"������� - "+restammunition+" ������");
		}
	}
	void addAmmunition(int n) { // ���������� ������������
		if (restammunition + n <= ammunition) {
			restammunition += n;
			System.out.println("����������� �������� �� "+n+" ������.\n�����: "+restammunition+" ������");
		} else System.out.println("���������� ������� ������������:"+restammunition);
	}
	void show() {
		
	}
	void showfield(JTextField[] txtfield){
		
	}	
}
// ���������
class Aircraft extends Military implements Air {
	int number_airplane; // ���-�� ���������
	int airplane = 0; // ����������� ���-�� ���������
	Aircraft(int[] s,double m,double f,double oF, int sp,int[][] c, int am, int plane){
		super(s,m,f,oF,sp,c,am);
		number_airplane = plane;
		airplane = plane;
	}
	void addAmmunition(int n) { // ���������� ������������
		if (restammunition + n <= ammunition) {
			restammunition += n;
			System.out.println("����������� �������� �� "+n+" ������.\n�����: "+restammunition+" ������");
		} else System.out.println("���������� ������� ������������:"+restammunition);
		if (airplane < number_airplane) airplane += number_airplane - airplane; 
	}
	void shooting(int n) { // �������� -> ������ ������������
		if (restammunition - n < 0) {
			System.out.println("�������� �������� ������ ����������, \n������������ �����������"+restammunition);
		} else {
			restammunition -= n;
			System.out.println("�������� ������������. �������������-"+n+" ������\n" +
					"������� - "+restammunition);
		}
		if (airplane > 0) {
			take_off(1);
			System.out.println("������� ��� ������ ������ � ������");
		} else System.out.println("��� �������� � �������: "+airplane+" - ������ ����������");
	}
	public void take_off(int a) { // �����
		if (airplane >= a) {
			airplane -= a;
			System.out.println("������� � ������ "+a+" ���������"+"\n������� �� ���������: "+airplane);
		} else System.out.println("��� �������� ������� � ������, ���-�� �� �����: "+airplane);
	}
	public void landing(int a) { // �������
		if (a <= number_airplane - airplane) {
			airplane += a;
			System.out.println("������� ����������� "+a+" ���������"+"\n������� �� ���������: "+airplane);
		} else {
			System.out.println("�� ������� ������� "+(number_airplane - airplane));
			System.out.println("�� ������� �� ������� "+(a-(number_airplane - airplane))+
					"\n��� ���������� ����� ������,");
			airplane = number_airplane;
			System.out.println("��������� �� ����� "+airplane);
		}
	}
	void show() {
		
	}
	void showfield(JTextField[] txtfield){
		
	}
}
// ������������ �����
class FishShip extends Truck implements Fishing {
	String[][] vid_fish; // 0 - ��� ����, 1 - ����� ����, 2 - ���������� ���������
	FishShip(int[] s,double m,double f,double oF, int sp,int[][] c,double trum, String[][] v_fish) {
		super(s, m, f, oF, sp, c, trum);
		this.vid_fish = new String[v_fish.length][v_fish[0].length];
		for (int i=0; i<vid_fish.length; i++){
			for (int j=0; j<vid_fish[0].length; j++) {
				this.vid_fish[i][j] = v_fish[i][j];
			} 
		}
	}
	public void fishing(String[] v_fish) { // ������� 0 -����� � vid_fish, 1 - �����
		  if (Integer.parseInt(vid_fish[(int)Integer.parseInt(v_fish[0])][1])-Integer.parseInt(vid_fish[(int)Integer.parseInt(v_fish[0])][2]) 
				>= Integer.parseInt(v_fish[1])) {
			loading(Double.parseDouble(v_fish[1]),vid_fish[(int)Integer.parseInt(v_fish[0])][0]);
			int temp = Integer.parseInt(vid_fish[(int)Integer.parseInt(v_fish[0])][2])
					+Integer.parseInt(v_fish[1]);
			vid_fish[(int)Integer.parseInt(v_fish[0])][2] = ""+temp;
			for (int i=0; i<vid_fish.length; i++){
				for (int j=0; j<vid_fish[0].length; j++) {
					System.out.print(vid_fish[i][j]+" |");
				}
				System.out.println();
			}
		} else System.out.println("��� ����:"+vid_fish[(int)Integer.parseInt(v_fish[0])][0]+" ��� ��������");
	}
	void loading(double n, String t) { // ��������
		if (resttrum - n > 0) {
			massGruz +=n;
			resttrum -= n;
			gruz += t+"|";
			System.out.println("���� "+t+" ������ "+n+" ��������");
		} else System.out.println("������ ��������,\n������� ����� ��� �����:"+resttrum);
	}
	void unloading(double n, String t) { // �������� 
		for(int i=0; i<vid_fish.length; i++){
			if (t.equals(vid_fish[i][0])){
				if (Double.parseDouble(vid_fish[i][2]) >= n) {
					vid_fish[i][2] = ""+(Double.parseDouble(vid_fish[i][2]) - n); 
					trum -= n;
					massGruz -=n;
					resttrum +=n;
					gruz = gruz.replaceAll(t,"");					
					System.out.println("����������: "+t+" "+n+" ����\n������� ����� �����:"+massGruz+" ����\n"+
					"������� ����� �� ������� "+resttrum+" ����");
					for(int j=0; j<vid_fish.length; j++) {
						System.out.println("������� ����� ���� ����:"+vid_fish[j][0]+
								" - "+vid_fish[j][2]+" ����");
					}
				}
			}
		}
	}
	void show() {
		
	}	
	void showfield(JTextField[] txtfield){
		
	}
}
public class Ships {
	JPanel mainPanel;
	JFrame theFrame;
	ArrayList<JCheckBox> checkboxList;
	String[] shipvid = {"Liner","Yaht","Tanker","Barge","Fishship","Aircraft","Esminec"};
	String[] img_url = {"c:/Liner3.png","c:/Yacht2.png","c:/Tanker2.png","c:/Barge2.png","c:/Fishship3.png","c:/Aircraft2.png","c:/Esminec2.png"};
	ImageIcon[] img = new ImageIcon[7];
	
	public void buildGUI_win1(){
		theFrame = new JFrame("Ships");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20));
		
		checkboxList = new ArrayList<JCheckBox>();
		JPanel buttonPanel = new JPanel();
		
		JButton start = new JButton("Ok");
		buttonPanel.add(start);
		JButton end = new JButton("Cansel");
		buttonPanel.add(end);
		
		for (int i=0; i<img_url.length; i++) {
			img[i] = new ImageIcon(img_url[i]);
		}
		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for (int i=0; i<shipvid.length; i++) {
			nameBox.add(new JLabel(shipvid[i],img[i], JLabel.HEIGHT)); 
		}
		
		background.add(BorderLayout.SOUTH, buttonPanel);
		background.add(BorderLayout.WEST, nameBox);
		
		theFrame.getContentPane().add(background);
		
		GridLayout grid = new GridLayout(7,15);
		grid.setVgap(1);
		grid.setHgap(2);
		mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER, mainPanel);
		// ������
		for (int i=0; i<shipvid.length; i++) {
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c); 
		}
		theFrame.setBounds(50,50,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}
	
	JFrame Win2frame;
	JPanel Win2panel;
	JTextField[] textfield;
	JPanel buttonPanel; 
	JButton[] buttons;
	
	public void buildGUI_win2(ImageIcon icon, String[] label, Ship obj, String[] button) {
		textfield = new JTextField[label.length];
		Win2frame = new JFrame("Fraht");
		Win2frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		Win2panel = new JPanel(layout);
		Box boxfield = new Box(BoxLayout.Y_AXIS);
		Box boxlabel = new Box(BoxLayout.Y_AXIS);
		JLabel vid = new JLabel("",icon,JLabel.CENTER);
		ImageIcon icLabel = new ImageIcon("c:/111111.png");
		for (int i=0; i<label.length; i++) {
			boxlabel.add(new JLabel(label[i],icLabel,JLabel.HEIGHT));
			textfield[i] = new JTextField();
			boxfield.add(textfield[i]);
		}
		obj.showfield(textfield);
		buttonPanel = new JPanel(); 
		buttons = new JButton[button.length];
		for (int i=0; i<button.length; i++) {
			buttons[i] = new JButton(button[i]);
			buttonPanel.add(buttons[i]);
		} 
		Win2panel.add(BorderLayout.NORTH,vid);
		Win2panel.add(BorderLayout.WEST,boxlabel);
		Win2panel.add(BorderLayout.EAST,boxfield);
		Win2panel.add(BorderLayout.SOUTH, buttonPanel);
		Win2frame.getContentPane().add(Win2panel);
		Win2frame.setBounds(60, 60, 300, 300);
		Win2frame.pack();
		Win2frame.setVisible(true);
	}
	public static void main(String[] args) {
		new Ships().buildGUI_win1();
		ImageIcon l = new ImageIcon("c:/Liner3.png");
		String[] namefield = {"�������������: ","�����: ","������: ","������������ ��������: ",
				"������������ �����: ","���� �� �����: ","����� ������������ ����: ",
				"�� ��� ��������� ����: ","��� ������: ","���� �����������: ","���� ������������������ ��������: ",
				"����: ","���������: ","������ �������: ","�� ����� �����: "};
		String[] name_button = {"Fraht","Embarkation","Disembarkation","Cansel"};
		// �������� �����
		System.out.println("������");
		int[] s = {1000, 200, 1000};
		int[][] c = {{0,6},{10,2}}; // �� ���� � ���� - ������
		int[] dist_s = new int[2];
		int[] dist_e = new int[2];
		dist_s[0] = c[0][0];
		dist_s[1] = c[0][1];
		dist_e[0] = c[1][0];
		dist_e[1] = c[1][1];		
		Liner Lainer1 = new Liner(s,1000,100000,70,60,c,1000,4,250);
		Lainer1.dateEnd(dist_s, dist_e, 2014, 00, 23, 22, 36);
		System.out.println("���� ��������: "+Lainer1.dateEnd.getTime());
		System.out.println("���������: "+Lainer1.distance(dist_s, dist_e));
		System.out.println("������ �������: "+Lainer1.outlayFuel(dist_s, dist_e));
		System.out.println("������� �������: "+(Lainer1.fuel-Lainer1.outlayFuel(dist_s, dist_e)));
		Lainer1.show();
		Lainer1.embarkation(300);
		System.out.println("������� ��������� ����: "+Lainer1.restplays());
		Lainer1.show();
		Lainer1.disembarkation(30);
		System.out.println("������� ��������� ����: "+Lainer1.restplays());
		Lainer1.show();
		new Ships().buildGUI_win2(l, namefield, Lainer1,name_button);
		// �������� ������
		l = new ImageIcon("c:/Tanker2.png");
		String[] namefield_T = {"�������������: ","�����: ","������: ","������������ ��������: ",
		"���-�� ������: ","E������ �����: ","������� �����: ","������� ������� �����: ","������������ �����: ",
		"����� �����: ","���� �����������: ","���� ������������������ ��������: ",
		"����: ","���������: ","������ �������: "};
		name_button[0] = "Fraht";
		name_button[1] = "Loading";
		name_button[2] = "Unloading";
		name_button[3] = "Cansel";
		System.out.println("������");
		s[0] = 1000;
		s[1] = 200;
		s[2] = 10;
		int[][] t_c = {{107,11},{114,22}}; // �� ������� (�������) � ������ (�����)
		dist_s[0] = t_c[0][0];
		dist_s[1] = t_c[0][1];
		dist_e[0] = t_c[1][0];
		dist_e[1] = t_c[1][1];	
		Tanker Tanker1 = new Tanker(s,20000,10000,70,40,t_c,1000,4);
		Tanker1.dateEnd(dist_s, dist_e, 2014, 00, 23, 22, 36);
		System.out.println("���� ��������: "+Tanker1.dateEnd.getTime());
		System.out.println("���������: "+Tanker1.distance(dist_s, dist_e));
		System.out.println("������ �������: "+Tanker1.outlayFuel(dist_s, dist_e));
		System.out.println("������� �������: "+(Tanker1.fuel-Tanker1.outlayFuel(dist_s, dist_e)));
		Tanker1.show();
		Tanker1.loading(300, "�����");
		Tanker1.show();
		Tanker1.unloading(100, "�����");
		Tanker1.show();
		new Ships().buildGUI_win2(l, namefield_T, Tanker1,name_button);
		// �������� ���������
		System.out.println("���������");
		s[0] = 1000;
		s[1] = 200;
		s[2] = 10;
		int[][] a_c = {{23,21},{19,-34}}; // ����-����� (����������) � �������� (���)
		dist_s[0] = a_c[0][0];
		dist_s[1] = a_c[0][1];
		dist_e[0] = a_c[1][0];
		dist_e[1] = a_c[1][1];	
		Aircraft Aircraft1 = new Aircraft(s,20000,10000,70,40,a_c,10000,20);
		Aircraft1.dateEnd(dist_s, dist_e, 2014, 00, 23, 00, 21);
		System.out.println("���� ��������: "+Aircraft1.dateEnd.getTime());
		System.out.println("���������: "+Aircraft1.distance(dist_s, dist_e));
		System.out.println("������ �������: "+Aircraft1.outlayFuel(dist_s, dist_e));
		System.out.println("������� �������: "+(Aircraft1.fuel-Aircraft1.outlayFuel(dist_s, dist_e)));
		Aircraft1.shooting(3000);
		Aircraft1.addAmmunition(1000);
		Aircraft1.take_off(11);
		Aircraft1.landing(3);
		Aircraft1.landing(15);
		// ��������� ������������ �����
		System.out.println("������������ ������");
		s[0] = 1000;
		s[1] = 200;
		s[2] = 10;
		int[][] f_c = {{23,21},{19,-34}}; // ����-����� (����������) � �������� (���)
		dist_s[0] = f_c[0][0];
		dist_s[1] = f_c[0][1];
		dist_e[0] = f_c[1][0];
		dist_e[1] = f_c[1][1];	
		String[][] vid_f = {{"�����","400","0"},{"������","400","0"},{"������","200","0"}};
		FishShip FishShip1 = new FishShip(s,2000,6000,70,20,f_c,1000,vid_f);
		for (int i=0; i<FishShip1.vid_fish.length; i++){
			for (int j=0; j<FishShip1.vid_fish[0].length; j++) {
				System.out.print(FishShip1.vid_fish[i][j]+" |");
			}
			System.out.println();
		}
		FishShip1.dateEnd(dist_s, dist_e, 2014, 00, 23, 00, 21);
		System.out.println("���� ��������: "+FishShip1.dateEnd.getTime());
		System.out.println("���������: "+FishShip1.distance(dist_s, dist_e));
		System.out.println("������ �������: "+FishShip1.outlayFuel(dist_s, dist_e));
		System.out.println("������� �������: "+(FishShip1.fuel-FishShip1.outlayFuel(dist_s, dist_e)));
		String[] v_f = {"0","200"};
		FishShip1.fishing(v_f);
		v_f[0] = "0";
		v_f[1] = "100";
		FishShip1.fishing(v_f);
		v_f[0] = "0";
		v_f[1] = "100";
		FishShip1.fishing(v_f);
		v_f[0] = "0";
		v_f[1] = "100";
		FishShip1.fishing(v_f);
		v_f[0] = "1";
		v_f[1] = "100";
		FishShip1.fishing(v_f);
		v_f[0] = "1";
		v_f[1] = "250";
		FishShip1.fishing(v_f);
		v_f[0] = "2";
		v_f[1] = "110";
		FishShip1.fishing(v_f);
		v_f[0] = "2";
		v_f[1] = "70";
		FishShip1.fishing(v_f);
		FishShip1.unloading(130, "������");
		FishShip1.unloading(150, "�����");
		FishShip1.unloading(150, "������");
		// ��������� �������
		System.out.println("�������");
		s[0] = 1000;
		s[1] = 200;
		s[2] = 10;
		int[][] e_c = {{23,21},{19,-34}}; // ����-����� (����������) � �������� (���)
		dist_s[0] = e_c[0][0];
		dist_s[1] = e_c[0][1];
		dist_e[0] = e_c[1][0];
		dist_e[1] = e_c[1][1];
		Esminec Esminec1 = new Esminec(s,10000,10000,90,70,a_c,10000);
		Esminec1.dateEnd(dist_s, dist_e, 2014, 00, 23, 00, 21);
		System.out.println("���� ��������: "+Esminec1.dateEnd.getTime());
		System.out.println("���������: "+Esminec1.distance(dist_s, dist_e));
		System.out.println("������ �������: "+Esminec1.outlayFuel(dist_s, dist_e));
		System.out.println("������� �������: "+(Esminec1.fuel-Esminec1.outlayFuel(dist_s, dist_e)));
		Esminec1.shooting(3000);
		Esminec1.addAmmunition(1000);
		Esminec1.addAmmunition(5000);
		// ����
		System.out.println("�������� ����� \"��������\"");
		s[0] = 35;
		s[1] = 7;
		s[2] = 8;
		int[][] y_c = {{23,21},{19,-34}}; // ����-����� (����������) � �������� (���)
		dist_s[0] = y_c[0][0];
		dist_s[1] = y_c[0][1];
		dist_e[0] = y_c[1][0];
		dist_e[1] = y_c[1][1];
		Yacht Yacht_Argonaft = new Yacht(s,80,1000,50,25,y_c,15,1);
		Yacht_Argonaft.dateEnd(dist_s, dist_e, 2014, 00, 23, 00, 21);
		System.out.println("���� ��������: "+Yacht_Argonaft.dateEnd.getTime());
		System.out.println("���������: "+Yacht_Argonaft.distance(dist_s, dist_e));
		System.out.println("������ �������: "+Yacht_Argonaft.outlayFuel(dist_s, dist_e));
		System.out.println("������� �������: "+(Yacht_Argonaft.fuel-Yacht_Argonaft.outlayFuel(dist_s, dist_e)));
		System.out.println("��� ���� - "+Yacht_Argonaft.v_yacht);
		System.out.println("���-�� ������������ ���� - "+Yacht_Argonaft.passenger);
		System.out.println("���-�� ����������: "+Yacht_Argonaft.restpassenger);
		Yacht_Argonaft.embarkation(5);
		Yacht_Argonaft.embarkation(10);
		Yacht_Argonaft.disembarkation(3);
		System.out.println("���-�� ����������: "+Yacht_Argonaft.restpassenger);
		System.out.println("������� ����: "+Yacht_Argonaft.restplays());
		// �����
		System.out.println("�����");
		l = new ImageIcon("c:/Barge2.png");
		String[] namefield_B = {"�������������: ","�����: ","������: ","������������ ��������: ",
		"������� �����: ","������� ������� �����: ","������������ �����: ",
		"����� �����: ","���� �����������: ","���� ������������������ ��������: ",
		"����: ","���������: ","������ �������: "};
		name_button[0] = "Fraht";
		name_button[1] = "Loading";
		name_button[2] = "Unloading";
		name_button[3] = "Cansel";
		s[0] = 100;
		s[1] = 20;
		s[2] = 8;
		int[][] b_c = {{23,21},{19,-34}}; // ����-����� (����������) � �������� (���)
		dist_s[0] = b_c[0][0];
		dist_s[1] = b_c[0][1];
		dist_e[0] = b_c[1][0];
		dist_e[1] = b_c[1][1];
		Barge Barge1 = new Barge(s,180,1000,50,10,b_c,1000);
		Barge1.dateEnd(dist_s, dist_e, 2014, 00, 23, 00, 21);
		System.out.println("���� ��������: "+Barge1.dateEnd.getTime());
		System.out.println("���������: "+Barge1.distance(dist_s, dist_e));
		System.out.println("������ �������: "+Barge1.outlayFuel(dist_s, dist_e));
		System.out.println("������� �������: "+(Barge1.fuel-Barge1.outlayFuel(dist_s, dist_e)));
		System.out.println("���� ����� - "+Barge1.gruz);
		Barge1.show();
		Barge1.loading(300, "������");
		Barge1.show_trum();
		Barge1.show();
		Barge1.loading(200, "�����");
		Barge1.show_trum();
		Barge1.show();
		Barge1.loading(300, "������");
		Barge1.show_trum();
		Barge1.show();
		Barge1.unloading(600,"������");
		Barge1.show_trum();
		Barge1.show();
		Barge1.unloading(200,"�����");
		Barge1.show_trum();
		Barge1.show();
		new Ships().buildGUI_win2(l, namefield_B, Barge1,name_button);
	}
}
