package edu.byu.cs.superasteroids.ship_builder;

/**
 * Created by Jk on 2/19/2016.
 */
public class ShipBuilderStates {

    interface State {
        IShipBuildingView.PartSelectionView partSelectionView = null;
        State getNext();
        IShipBuildingView.PartSelectionView getNextView();
        State getPrev();
        IShipBuildingView.PartSelectionView getPrevView();
        IShipBuildingView.PartSelectionView getPartSelectionView();
    }
    enum States implements State{
        MAIN_BODY_STATE{
            @Override
            public State getNext() {
                return EXTRA_PART_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getNextView() {
                return IShipBuildingView.PartSelectionView.EXTRA_PART;
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
            public IShipBuildingView.PartSelectionView getPartSelectionView() {
                return IShipBuildingView.PartSelectionView.MAIN_BODY;
            }
        },
        EXTRA_PART_STATE{
            @Override
            public State getNext() {
                return CANNON_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getNextView() {
                return IShipBuildingView.PartSelectionView.CANNON;
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
            public IShipBuildingView.PartSelectionView getPartSelectionView() {
                return null;
            }
        },
        CANNON_STATE{
            @Override
            public State getNext() {
                return ENGINE_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getNextView() {
                return IShipBuildingView.PartSelectionView.ENGINE;
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
            public IShipBuildingView.PartSelectionView getPartSelectionView() {
                return IShipBuildingView.PartSelectionView.CANNON;
            }
        },
        ENGINE_STATE{
            @Override
            public State getNext() {
                return POWER_CORE_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getNextView() {
                return IShipBuildingView.PartSelectionView.POWER_CORE;
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
            public IShipBuildingView.PartSelectionView getPartSelectionView() {
                return IShipBuildingView.PartSelectionView.ENGINE;
            }
        },
        POWER_CORE_STATE{
            @Override
            public State getNext() {
                return MAIN_BODY_STATE;
            }

            @Override
            public IShipBuildingView.PartSelectionView getNextView() {
                return IShipBuildingView.PartSelectionView.MAIN_BODY;
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
            public IShipBuildingView.PartSelectionView getPartSelectionView() {
                return IShipBuildingView.PartSelectionView.POWER_CORE;
            }
        }
    }
}
