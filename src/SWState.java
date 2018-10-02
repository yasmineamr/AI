import java.util.ArrayList;

public class SWState extends State{
	Coordinates jon;
	int dragonglasses;
	ArrayList<Coordinates> whitewalkers;
	
	public SWState(Coordinates j, int dg, ArrayList<Coordinates> ww) {
		jon = j;
		dragonglasses = dg;
		
		whitewalkers = new ArrayList<>();
		for(int i = 0; i < ww.size(); i++)
			whitewalkers.add(ww.get(i));
	}
}