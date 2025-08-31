import java.util.List;

public class AnyAll {
    public List<Condition> all;
    public List<Condition> any;

    public boolean evaluate(SystemContext ctx) {
        if (all != null && !all.isEmpty()) {
            for (Condition c : all) {
                if (!c.evaluate(ctx)) return false;
            }
        }
        if (any != null && !any.isEmpty()) {
            boolean anyTrue = false;
            for (Condition c : any) {
                if (c.evaluate(ctx)) { anyTrue = true; break; }
            }
            if (!anyTrue) return false;
        }
        return true;
    }
}



