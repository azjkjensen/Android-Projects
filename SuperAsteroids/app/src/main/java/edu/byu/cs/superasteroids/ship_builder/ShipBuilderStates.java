package edu.byu.cs.superasteroids.ship_builder;

/**
 * Created by Jk on 2/19/2016.
 */
public class ShipBuilderStates {

    private static final String MAIN_BODY_NAME = "Main Body";
    private static final String EXTRA_PART_NAME = "Left Wing";
    private static final String CANNON_NAME = "Cannon";
    private static final String ENGINE_NAME = "Engine";
    private static final String POWER_CORE_NAME = "Power Core";

    interface State {
        String getName();
        State getNext();
        IShipBuildingView.PartSelectionView getNextView();
        String getNextName();
        State getPrev();
        IShipBuildingView.PartSelectionView getPrevView();
        String getPrevName();
        IShipBuildingView.PartSelectionView getPartSelectionView();
    }
    enum States implements State{
        MAIN_BODY_STATE{
            @Override
            public String getName() {
                return MAIN_BODY_NAME;
            }

            @Override
            public State getNext() {
                return EXTRA_PART_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getNextView() {
                return IShipBuildingView.PartSelectionView.EXTRA_PART;
            }

            @Override
            public String getNextName() {
                return EXTRA_PART_NAME;
            }

            @Override
            public State getPrev() {
                return POWER_CORE_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getPrevView() {
                return IShipBuildingView.PartSelectionView.POWER_CORE;
            }

            @Override
            public String getPrevName() {
                return POWER_CORE_NAME;
            }

            @Override
            public IShipBuildingView.PartSelectionView getPartSelectionView() {
                return IShipBuildingView.PartSelectionView.MAIN_BODY;
            }
        },
        EXTRA_PART_STATE{
            @Override
            public String getName() {
                return EXTRA_PART_NAME;
            }

            @Override
            public State getNext() {
                return CANNON_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getNextView() {
                return IShipBuildingView.PartSelectionView.CANNON;
            }

            @Override
            public String getNextName() {
                return CANNON_NAME;
            }

            @Override
            public State getPrev() {
                return MAIN_BODY_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getPrevView() {
                return IShipBuildingView.PartSelectionView.MAIN_BODY;
            }

            @Override
            public String getPrevName() {
                return MAIN_BODY_NAME;
            }

            @Override
            public IShipBuildingView.PartSelectionView getPartSelectionView() {
                return IShipBuildingView.PartSelectionView.EXTRA_PART;
            }
        },
        CANNON_STATE{
            @Override
            public String getName() {
                return CANNON_NAME;
            }

            @Override
            public State getNext() {
                return ENGINE_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getNextView() {
                return IShipBuildingView.PartSelectionView.ENGINE;
            }

            @Override
            public String getNextName() {
                return ENGINE_NAME;
            }

            @Override
            public State getPrev() {
                return EXTRA_PART_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getPrevView() {
                return IShipBuildingView.PartSelectionView.EXTRA_PART;
            }

            @Override
            public String getPrevName() {
                return EXTRA_PART_NAME;
            }

            @Override
            public IShipBuildingView.PartSelectionView getPartSelectionView() {
                return IShipBuildingView.PartSelectionView.CANNON;
            }
        },
        ENGINE_STATE{
            @Override
            public String getName() {
                return ENGINE_NAME;
            }

            @Override
            public State getNext() {
                return POWER_CORE_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getNextView() {
                return IShipBuildingView.PartSelectionView.POWER_CORE;
            }

            @Override
            public String getNextName() {
                return POWER_CORE_NAME;
            }

            @Override
            public State getPrev() {
                return CANNON_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getPrevView() {
                return IShipBuildingView.PartSelectionView.CANNON;
            }

            @Override
            public String getPrevName() {
                return CANNON_NAME;
            }

            @Override
            public IShipBuildingView.PartSelectionView getPartSelectionView() {
                return IShipBuildingView.PartSelectionView.ENGINE;
            }
        },
        POWER_CORE_STATE{
            @Override
            public String getName() {
                return POWER_CORE_NAME;
            }

            @Override
            public State getNext() {
                return MAIN_BODY_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getNextView() {
                return IShipBuildingView.PartSelectionView.MAIN_BODY;
            }

            @Override
            public String getNextName() {
                return MAIN_BODY_NAME;
            }

            @Override
            public State getPrev() {
                return ENGINE_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getPrevView() {
                return IShipBuildingView.PartSelectionView.ENGINE;
            }

            @Override
            public String getPrevName() {
                return ENGINE_NAME;
            }

            @Override
            public IShipBuildingView.PartSelectionView getPartSelectionView() {
                return IShipBuildingView.PartSelectionView.POWER_CORE;
            }
        }
    }
}
