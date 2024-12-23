import javax.swing.JOptionPane;

public class MojSwiat {

	public static void main(String[] args)
	{
		int rozmiar_x=0, rozmiar_y=0;
        while (rozmiar_x <= 7 || rozmiar_y <= 7)
        {
            rozmiar_x = Integer.valueOf(JOptionPane.showInputDialog("Podaj rozmiar x>7: "));
            rozmiar_y = Integer.valueOf(JOptionPane.showInputDialog("Podaj rozmiar y>7: "));
            if(rozmiar_x <= 7 || rozmiar_y <= 7)
            	JOptionPane.showMessageDialog(null, "Podaj liczby wieksze niz 7 ","Jeszcze raz", JOptionPane.INFORMATION_MESSAGE);
        }
        Swiat swiat = new Swiat(rozmiar_x, rozmiar_y);
        swiat.wykonajTure();
        swiat.aktualizujPlansze();
        //swiat.rysujSwiat();
        swiat.aktualizujFrame();
        swiat.wypiszLogiIUsun();
        //swiat.wypiszOrganizmy(); //pierwsza tura potem na klikniecie strzalek kolejne sie wykonuja
    }
}
