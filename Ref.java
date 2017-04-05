import java.util.ArrayList;
import absyn.*;

public class Ref {
	public Exp exp;
	public Frame belongsTo;

	Ref (Exp exp, Frame frame)
	{
		this.exp = exp;
		belongsTo = frame;
	}
}
