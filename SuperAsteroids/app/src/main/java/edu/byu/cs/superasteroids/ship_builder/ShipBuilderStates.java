package edu.byu.cs.superasteroids.ship_builder;

/**
 * Created by Jk on 2/19/2016.
 */
public class ShipBuilderStates {

    interface State {
        IShipBuildingView.PartSelectionView partSelectionView = null;
    }
    enum States implements State{
        MAIN_BODY_STATE, EXTRA_PART_STATE, CANNON_STATE, ENGINE_STATE, POWER_CORE_STATE
    }
}
