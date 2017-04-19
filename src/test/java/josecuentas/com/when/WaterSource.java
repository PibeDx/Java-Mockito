package josecuentas.com.when;

/**
 * Created by jcuentast on 19/04/17.
 */
public class WaterSource {
    public int getWaterPressure() {
        return 0;
    }

    public void doSelfCheck() throws WaterException {
        throw new WaterException();
    }
}
