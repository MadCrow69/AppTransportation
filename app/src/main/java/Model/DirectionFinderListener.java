package Model;

import java.util.List;

/**
 * Created by Mai Thanh Hiep on 4/3/2016.
 * Edit by Diego O. Antunes on 07/07/2018
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
