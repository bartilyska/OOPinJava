import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Swiat implements ActionListener,KeyListener 
{
	private ArrayList<ArrayList<Character>> plansza = new ArrayList<>();
	private ArrayList<Organizm> organizmy = new ArrayList<>();
	private ArrayList<String> logi = new ArrayList<>();
	private int rozmiar_x;
	private int rozmiar_y;
	private String kierunek_czlowieka;
	private int cooldown_mocy;
	
	private JFrame frame = new JFrame("Bartosz Lyskanowski 198051");
	private JPanel mapa = new JPanel();
	private JPanel zdarzenia = new JPanel();
	private JTextArea logi_plansza = new JTextArea();
	private JPanel panel_na_sterowanie = new JPanel();
	private JButton[][] pola;
	private JButton[] guziki_sterowania;
    
	public Swiat(int x,int y)
	{
		rozmiar_x=x;
		rozmiar_y=y;
		kierunek_czlowieka="";
		cooldown_mocy=0;
		for (int i = 0; i < rozmiar_y; i++) 
		{
            plansza.add(new ArrayList<Character>());
        }
		for (int i = 0; i < rozmiar_y; i++) 
		{
            for (int j = 0; j < rozmiar_x; j++) 
            {
                plansza.get(i).add('.');
            }
        }
		/*organizmy.add(new Wilk(0, 2,this));
		organizmy.add(new Owca(0, 3,this));
		organizmy.add(new Owca(0, 4,this));
		organizmy.add(new Lis(0, 5,this));
		organizmy.add(new Zolw(1, 6,this));
		organizmy.add(new Czlowiek(2, 6,this));
		organizmy.add(new WilczeJagody(4, 6,this));
		organizmy.add(new BarszczSosnowskiego(3, 6,this));
		organizmy.add(new Antylopa(8, 6,this));
		organizmy.add(new Antylopa(7, 6,this));
		organizmy.add(new Antylopa(1, 8,this));
		organizmy.add(new Guarana(2, 7,this));*/
		Random rand = new Random();
		ArrayList<Pair<Integer,Integer>>pozycje = new ArrayList<>();
		for (int i = 0; i < y; i++)
		{
			for (int j = 0; j < x; j++)
			{
				pozycje.add(new Pair<>(i, j)) ;
			}
		}
		int ileorg = x * y / 50;
		int indeks = rand.nextInt(pozycje.size());
		Pair<Integer,Integer> para = pozycje.get(indeks);
		pozycje.remove(indeks);
		organizmy.add(new Czlowiek(para.getSecond(), para.getFirst(), this));
		ArrayList<Pair<Integer,Integer>>wylosowane = new ArrayList<>();
		for (int i = 0; i < ileorg; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				indeks = rand.nextInt(pozycje.size());
				wylosowane.add(pozycje.get(indeks));
				pozycje.remove(indeks);
			}
			organizmy.add(new Antylopa(wylosowane.get(0).getSecond(), wylosowane.get(0).getFirst(), this));
			organizmy.add(new BarszczSosnowskiego(wylosowane.get(1).getSecond(), wylosowane.get(1).getFirst(), this));
			organizmy.add(new Guarana(wylosowane.get(2).getSecond(), wylosowane.get(2).getFirst(), this));
			organizmy.add(new Lis(wylosowane.get(3).getSecond(), wylosowane.get(3).getFirst(), this));
			organizmy.add(new Mlecz(wylosowane.get(4).getSecond(), wylosowane.get(4).getFirst(), this));
			organizmy.add(new Owca(wylosowane.get(5).getSecond(), wylosowane.get(5).getFirst(), this));
			organizmy.add(new Trawa(wylosowane.get(6).getSecond(), wylosowane.get(6).getFirst(), this));
			organizmy.add(new WilczeJagody(wylosowane.get(7).getSecond(), wylosowane.get(7).getFirst(), this));
			organizmy.add(new Wilk(wylosowane.get(8).getSecond(), wylosowane.get(8).getFirst(), this));
			organizmy.add(new Zolw(wylosowane.get(9).getSecond(), wylosowane.get(9).getFirst(), this));
			wylosowane.clear();
		}
		
		
		int ROZMIARX=1100,ROZMIARY=800;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(ROZMIARX,ROZMIARY);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.addKeyListener(this);
        //frame.setResizable(false);
        
        logi_plansza.setBackground(Color.BLACK);
        logi_plansza.setFont(new Font(Font.SANS_SERIF, Font.BOLD,10));
        logi_plansza.setForeground(Color.WHITE);
        logi_plansza.setOpaque(true);
        logi_plansza.setFocusable(false);
        logi_plansza.setLineWrap(true); 
        logi_plansza.setWrapStyleWord(true);
        
        mapa.setLayout(new GridLayout(rozmiar_y,rozmiar_x));
        mapa.setBackground(Color.DARK_GRAY);
        mapa.setPreferredSize(new Dimension(500,500));
        

        panel_na_sterowanie.setLayout(new GridLayout(1, 4));
        panel_na_sterowanie.setBackground(Color.gray);
        panel_na_sterowanie.setPreferredSize(new Dimension(220,120));
        
        zdarzenia.setLayout(new BorderLayout());
        zdarzenia.setPreferredSize(new Dimension(300,400));
        
        pola = new JButton[rozmiar_y][];
        for(int i=0;i<rozmiar_y; i++)
        {
            pola[i] = new JButton[rozmiar_x];
            for(int j=0; j<rozmiar_x; j++)
            {
                pola[i][j] = new JButton();
                mapa.add(pola[i][j]);
                pola[i][j].setFont(new Font(Font.DIALOG, Font.BOLD,13));
                pola[i][j].setFocusable(false);
                pola[i][j].setBackground(new Color(58,39,27));
                pola[i][j].addActionListener(this);
                pola[i][j].setText("");
            }
        }
        guziki_sterowania = new JButton[3];
        panel_na_sterowanie.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        for(int i=0;i<3; i++)
        {
        	guziki_sterowania[i] = new JButton();
        	guziki_sterowania[i].setPreferredSize(new Dimension(120, 50));
            panel_na_sterowanie.add(guziki_sterowania[i]);
            guziki_sterowania[i].setFont(new Font(Font.DIALOG, Font.BOLD,13));
            guziki_sterowania[i].setFocusable(false);
            guziki_sterowania[i].setBackground(Color.decode("#6441A5"));
            guziki_sterowania[i].setForeground(Color.CYAN);
            guziki_sterowania[i].addActionListener(this);
        }
        
        guziki_sterowania[0].setText("Zapisz");
        guziki_sterowania[1].setText("Wczytaj");
        guziki_sterowania[2].setText("Moc");
        
        zdarzenia.add(logi_plansza);
        frame.add(panel_na_sterowanie,BorderLayout.SOUTH);
        frame.add(zdarzenia,BorderLayout.EAST);
        frame.add(mapa,BorderLayout.CENTER);
	}
	static final Comparator<Organizm> PORZADEK = new Comparator<Organizm>()
	{
		public int compare(Organizm org1, Organizm org2)
		{
			if(org1.getInicjatywa()<org2.getInicjatywa())
					return 1;
			else if(org1.getInicjatywa()==org2.getInicjatywa())
			{
				if(org1.getWiek()<org2.getWiek())
					return 1;
				else if (org1.getWiek() == org2.getWiek())
	                return 0;
				else return -1;
			}
			else return -1;
		}
	};
	public void wykonajTure()
	{
		Collections.sort(organizmy,PORZADEK);
		for (int i = 0; i<organizmy.size(); i++)
		{
			if (organizmy.get(i).getZyje() == true)//martwe nie beda wykonywac akcji, czekaj na usuniecie
				organizmy.get(i).akcja();
		}
		for (int i = organizmy.size() - 1; i >= 0; i--) 
		{
			if (organizmy.get(i).getZyje()==false) //usun martwe
				organizmy.remove(i); // od konca nie bedzie kolizji
		}
	}
	public void aktualizujPlansze()
	{
		for (int i = 0; i < getRozmiar_y(); i++)
		{
			for (int j = 0; j < getRozmiar_x(); j++)
			{
				plansza.get(i).set(j,'.');
			}
		}
		for (int i = 0; i < organizmy.size(); i++)
		{
			dodajOrganizmNaPlansze(organizmy.get(i));
		}
	}
	public void aktualizujFrame()
	{
		for (int i = 0; i < getRozmiar_y(); i++)
		{
			for (int j = 0; j < getRozmiar_x(); j++)
			{
				if(plansza.get(i).get(j)=='.')
				{
					pola[i][j].setBackground(new Color(58,39,27)); //czyszczenie po rundzie
					pola[i][j].setText("");
				}
			}
		}
		if(getCooldownMoc()!=0)
			guziki_sterowania[2].setEnabled(false);
		else 
			guziki_sterowania[2].setEnabled(true);
		for (int i = 0; i < organizmy.size(); i++)
		{
			dodajOrganizmNaWidocznaPlansze(organizmy.get(i));
		}
	}
	Organizm coStoi(int x, int y)
	{
		if (plansza.get(y).get(x) == '.')
			return null;
		for (Organizm organizm : organizmy)
		{
			if (organizm.getPolozenie_y() == y && organizm.getPolozenie_x() == x && organizm.getZyje()==true)//organizm ma zyc a nie trup
				return organizm;
		}
		return null;
	}
	public void rysujSwiat()
	{
		System.out.println("Bartosz Lyskanowski s198051");
		for (int i = 0; i < getRozmiar_y(); i++)
		{
			for (int j = 0; j < getRozmiar_x(); j++)
			{
				System.out.print(plansza.get(i).get(j)); //konsolowe rysowanie
			}
			System.out.println();
		}
	}
	public void wypiszOrganizmy()
	{
		for (int i = 0; i < organizmy.size(); i++) 
		{
			organizmy.get(i).wypiszOrganizm();
		}
	}
	public int getCooldownMoc()
	{
		return cooldown_mocy;
	}
	public void setCooldownMoc(int moc)
	{
		cooldown_mocy = moc;
	}
	public void usunOrganizm(Organizm org) //oznacza zycie=false
	{
		org.setZyje(false);
	}
	public void dodajOrganizm(Organizm org)
	{
		organizmy.add(org);
	}
	public void dodajOrganizmNaWidocznaPlansze(Organizm org)
	{
		pola[org.getPolozenie_y()][org.getPolozenie_x()].setText(org.getOznacznie()+"");
		if(org instanceof Wilk)
			pola[org.getPolozenie_y()][org.getPolozenie_x()].setBackground(Color.LIGHT_GRAY);
		else if(org instanceof Owca)
			pola[org.getPolozenie_y()][org.getPolozenie_x()].setBackground(Color.WHITE);
		else if(org instanceof Lis)
			pola[org.getPolozenie_y()][org.getPolozenie_x()].setBackground(Color.ORANGE);
		else if(org instanceof Zolw)
			pola[org.getPolozenie_y()][org.getPolozenie_x()].setBackground(Color.decode("#006400"));
		else if(org instanceof Antylopa)
			pola[org.getPolozenie_y()][org.getPolozenie_x()].setBackground(Color.decode("#D3B683"));
		else if(org instanceof Czlowiek)
			pola[org.getPolozenie_y()][org.getPolozenie_x()].setBackground(Color.decode("#FFE5B4"));
		else if(org instanceof Trawa)
			pola[org.getPolozenie_y()][org.getPolozenie_x()].setBackground(Color.decode("#90ee90"));
		else if(org instanceof Mlecz)
			pola[org.getPolozenie_y()][org.getPolozenie_x()].setBackground(Color.YELLOW);
		else if(org instanceof Guarana)
			pola[org.getPolozenie_y()][org.getPolozenie_x()].setBackground(Color.RED);
		else if(org instanceof WilczeJagody)
			pola[org.getPolozenie_y()][org.getPolozenie_x()].setBackground(Color.BLACK);
		else if(org instanceof BarszczSosnowskiego)
			pola[org.getPolozenie_y()][org.getPolozenie_x()].setBackground(Color.decode("#E1D9D1"));
	}
	public void dodajOrganizmNaPlansze(Organizm org)
	{
		plansza.get(org.getPolozenie_y()).set(org.getPolozenie_x(),org.getOznacznie());
	}
	public void dodajOrganizmPoKliku(Organizm org)
	{
		dodajOrganizmNaPlansze(org);
		dodajOrganizm(org);
		dodajOrganizmNaWidocznaPlansze(org);
	}
	public int getRozmiar_x()
	{
		return rozmiar_x;
	}
	public int getRozmiar_y()
	{
		return rozmiar_y;
	}
	public String getKierunekCzlowieka()
	{
		return kierunek_czlowieka;
	}
	public void setKierunekCzlowieka(String ruch)
	{
		kierunek_czlowieka = ruch;
	}
	public void ustawOznaczenie(int x, int y,char oznaczenie)
	{
		plansza.get(y).set(x,oznaczenie);
	}
	public void usunOznaczenie(int x, int y)
	{
		plansza.get(y).set(x,'.');
	}
	public void dodajLog(String log)
	{
		logi.add(log);
	}
	public void wypiszLogiIUsun()
	{
		logi_plansza.setText("");
		/*for (int i = 0; i < logi.size(); i++)
		{
			System.out.println(logi.get(i));
		}*/
		for (int i = 0; i < logi.size(); i++)
		{
			logi_plansza.append(logi.get(i)+"\n");
		}
		logi.clear();
	}
	public void wypiszLogi()
	{
		logi_plansza.setText(""); //bez clear
		for (int i = 0; i < logi.size(); i++)
		{
			logi_plansza.append(logi.get(i)+"\n");
		}
	}
	public void zapiszDoPliku()
	{
		String nazwa = JOptionPane.showInputDialog("Podaj nazwe pliku do zapisu");
		try (PrintWriter zapis = new PrintWriter(nazwa))
		{
            zapis.println(rozmiar_x+" "+rozmiar_y+" "+cooldown_mocy);
            for (int i = 0; i < organizmy.size(); i++)
            {
            	if (organizmy.get(i) instanceof Czlowiek)
            	{
            		Czlowiek czlowiek = (Czlowiek) organizmy.get(i);
            		zapis.println(organizmy.get(i).getNazwa() + " " + organizmy.get(i).getPolozenie_x() + " " + organizmy.get(i).getPolozenie_y() + " " + 
            		organizmy.get(i).getWiek() + " " + organizmy.get(i).getSila() + " " +
            		organizmy.get(i).getInicjatywa() + " " + organizmy.get(i).getOznacznie() + " " +czlowiek.getIleJeszczeMocy());
            	}
            	else
            	{
            		zapis.println(organizmy.get(i).getNazwa() + " " + organizmy.get(i).getPolozenie_x() + " " + organizmy.get(i).getPolozenie_y() + " " + 
            		organizmy.get(i).getWiek() + " " + organizmy.get(i).getSila() + " " +
            		organizmy.get(i).getInicjatywa() + " " + organizmy.get(i).getOznacznie());
            	}
            }
            logi.add("Zapisano plansze do pliku");
            wypiszLogi();
        } 
		catch (IOException e)
		{
            System.err.println("Coś nie tak z plikiem " + e.getMessage());
        }
	}
	public void wczytZPliku()
	{
		String nazwapliku = JOptionPane.showInputDialog("Podaj nazwe pliku do zapisu");
		try (BufferedReader wczyt = new BufferedReader(new FileReader(nazwapliku))) 
		{
			organizmy.clear();
			plansza.clear();
			logi.clear();
			logi_plansza.setText("");
			String linia1 = wczyt.readLine();
            String[] rozbicie = linia1.split(" ");
            rozmiar_x = Integer.parseInt(rozbicie[0]);
            rozmiar_y = Integer.parseInt(rozbicie[1]);
            cooldown_mocy = Integer.parseInt(rozbicie[2]);
            for (int i = 0; i < rozmiar_y; i++) 
			{
	            plansza.add(new ArrayList<Character>());
	        }
			for (int i = 0; i < rozmiar_y; i++) 
			{
	            for (int j = 0; j < rozmiar_x; j++) 
	            {
	                plansza.get(i).add('.');
	            }
	        }
            mapa.removeAll();
            mapa.setLayout(new GridLayout(rozmiar_y,rozmiar_x));
            mapa.setBackground(Color.DARK_GRAY);
            mapa.setPreferredSize(new Dimension(500,500));
            pola = new JButton[rozmiar_y][];
            for(int i=0;i<rozmiar_y; i++)
            {
                pola[i] = new JButton[rozmiar_x];
                for(int j=0; j<rozmiar_x; j++)
                {
                    pola[i][j] = new JButton();
                    mapa.add(pola[i][j]);
                    pola[i][j].setFont(new Font(Font.DIALOG, Font.BOLD,13));
                    pola[i][j].setFocusable(false);
                    pola[i][j].setBackground(new Color(58,39,27));
                    pola[i][j].addActionListener(this);
                    pola[i][j].setText("");
                }
            }
            String linia;
            while ((linia = wczyt.readLine()) != null) 
            {
                String[] dane = linia.split(" ");
                String nazwa = dane[0];
                int x = Integer.parseInt(dane[1]);
                int y = Integer.parseInt(dane[2]);
                int wiek = Integer.parseInt(dane[3]);
                int sila = Integer.parseInt(dane[4]);
                int inicjatywa = Integer.parseInt(dane[5]);
                char oznaczenie = dane[6].charAt(0);
                if(nazwa.equals("Czlowiek"))
                {
                    int ile_jeszcze_mocy = Integer.parseInt(dane[7]);
                    organizmy.add(new Czlowiek(x, y, sila, inicjatywa, oznaczenie,wiek,nazwa,this,true,ile_jeszcze_mocy));
                }
                else if(nazwa.equals("Wilk"))
                	organizmy.add(new Wilk(x, y, sila, inicjatywa,oznaczenie, wiek, nazwa, this,true));
                else if(nazwa.equals("Owca"))
                	organizmy.add(new Owca(x, y, sila, inicjatywa,oznaczenie, wiek, nazwa, this,true));
                else if(nazwa.equals("Zolw"))
                	organizmy.add(new Zolw(x, y, sila, inicjatywa,oznaczenie, wiek, nazwa, this,true));
                else if(nazwa.equals("Lis"))
                	organizmy.add(new Lis(x, y, sila, inicjatywa,oznaczenie, wiek, nazwa, this,true));
                else if(nazwa.equals("Antylopa"))
                	organizmy.add(new Antylopa(x, y, sila, inicjatywa,oznaczenie, wiek, nazwa, this,true));
                else if(nazwa.equals("Trawa"))
                	organizmy.add(new Trawa(x, y, sila, inicjatywa,oznaczenie, wiek, nazwa, this,true));
                else if(nazwa.equals("Mlecz"))
                	organizmy.add(new Mlecz(x, y, sila, inicjatywa,oznaczenie, wiek, nazwa, this,true));
                else if(nazwa.equals("Guarana"))
                	organizmy.add(new Guarana(x, y, sila, inicjatywa,oznaczenie, wiek, nazwa, this,true));
                else if(nazwa.equals("WilczeJagody"))
                	organizmy.add(new WilczeJagody(x, y, sila, inicjatywa,oznaczenie, wiek, nazwa, this,true));
                else if(nazwa.equals("BarszczSosnowskiego"))
                	organizmy.add(new BarszczSosnowskiego(x, y, sila, inicjatywa,oznaczenie, wiek, nazwa, this,true));
            }   
            logi.add("Wczytano z pliku");
            //wypiszOrganizmy();
            wypiszLogiIUsun();
            aktualizujPlansze();
            //rysujSwiat();
            aktualizujFrame();
		}
		catch (IOException e) 
		{
            System.err.println("Cos nie tak z plikiem " + e.getMessage());
        }
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		kierunek_czlowieka="";
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			kierunek_czlowieka="lewo";
			break;
		case KeyEvent.VK_RIGHT:
			kierunek_czlowieka="prawo";
			break;
		case KeyEvent.VK_UP:
			kierunek_czlowieka="gora";
			break;
		case KeyEvent.VK_DOWN:
			kierunek_czlowieka="dol";
			break;
		}
		if(kierunek_czlowieka!="")
		{
			wykonajTure();
			//rysujSwiat();
        	aktualizujFrame();
        	wypiszLogiIUsun();
        	//wypiszOrganizmy();
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==guziki_sterowania[0]) //zapis
        {
            zapiszDoPliku();
        }
		if(e.getSource()==guziki_sterowania[1]) //wczyt
        {
            wczytZPliku();
        }
		if(e.getSource()==guziki_sterowania[2]) //moc
        {
            if(getCooldownMoc()==0)
            {
            	guziki_sterowania[2].setEnabled(false);
            	setCooldownMoc(10);
            }
        }
		
		for(int i=0; i<rozmiar_y; i++)
        {
            for(int j=0;j<rozmiar_x;j++)
            {
                if(e.getSource()==pola[i][j])
                {
                	if (coStoi(j, i) == null)
                    {
                		String[] wybor = {"Wilk", "Owca", "Lis", "Zolw", "Antylopa", "Trawa", "Mlecz", "Guarana", "WilczeJagody", "BarszczSosnowskiego"};
                        int opcja = JOptionPane.showOptionDialog (null, "Co wstawic", "Dodaj organizm:",
                        		JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, wybor, wybor[0]);
                        switch(opcja)
                        {
                            case 0:
                            	dodajOrganizmPoKliku(new Wilk(j,i, this));
                            	logi.add("Dodano Wilka");
                                break;
                            case 1:
                                dodajOrganizmPoKliku(new Owca(j,i, this));
                                logi.add("Dodano Owce");
                                break;
                            case 2:
                            	dodajOrganizmPoKliku(new Lis(j,i, this));
                            	logi.add("Dodano Lisa");
                                break;
                            case 3:
                            	dodajOrganizmPoKliku(new Zolw(j,i, this));
                            	logi.add("Dodano Zolwia");
                                break;
                            case 4:
                            	dodajOrganizmPoKliku(new Antylopa(j,i, this));
                            	logi.add("Dodano Antylope");
                                break;
                            case 5:
                            	dodajOrganizmPoKliku(new Trawa(j,i, this));
                            	logi.add("Dodano Trawe");
                                break;
                            case 6:
                            	dodajOrganizmPoKliku(new Mlecz(j,i, this));
                            	logi.add("Dodano Mlecza");
                                break;
                            case 7:
                            	dodajOrganizmPoKliku(new Guarana(j,i, this));
                            	logi.add("Dodano Guarane");
                                break;
                            case 8:
                            	dodajOrganizmPoKliku(new WilczeJagody(j,i, this));
                            	logi.add("Dodano WilczeJagody");
                                break;
                            case 9:
                            	dodajOrganizmPoKliku(new BarszczSosnowskiego(j,i, this));
                            	logi.add("Dodano BarszczSosnowskiego");
                                break;
                        }
                        
                    }
                }
            }
        }
	}
}
