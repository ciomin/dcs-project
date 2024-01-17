package Team_martomfibut;

import Components.*;
import DataObjects.DataInteger;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Controller1 {
    public static void main(String[] args) {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Controller1";
        pn.SetName("Controller1");
        pn.NetworkPort = 1082;

        DataString ini = new DataString();
        //ini.Printable = false;
        ini.SetName("ini");
        ini.SetValue("red");
        pn.ConstantPlaceList.add(ini);

        DataString red = new DataString();
        //red.Printable = false;
        red.SetName("red");
        red.SetValue("red");
        pn.ConstantPlaceList.add(red);

        DataString green = new DataString();
        //green.Printable = false;
        green.SetName("green");
        green.SetValue("green");
        pn.ConstantPlaceList.add(green);

        DataString yellow = new DataString();
        //yellow.Printable = false;
        yellow.SetName("yellow");
        yellow.SetValue("yellow");
        pn.ConstantPlaceList.add(yellow);

        DataInteger Five = new DataInteger();
        Five.SetName("Five");
        Five.SetValue(5);
        pn.ConstantPlaceList.add(Five);

        DataInteger Ten = new DataInteger();
        Ten.SetName("Ten");
        Ten.SetValue(10);
        pn.ConstantPlaceList.add(Ten);

        DataString p1 = new DataString();
        p1.SetName("r1r2");
        p1.SetValue("signal");
        pn.PlaceList.add(p1);

        DataString p2 = new DataString();
        p2.SetName("g1r2");
        pn.PlaceList.add(p2);

        DataString p3 = new DataString();
        p3.SetName("y1r2");
        pn.PlaceList.add(p3);

        DataString p4 = new DataString();
        p4.SetName("r1g2");
        pn.PlaceList.add(p4);

        DataString p5 = new DataString();
        p5.SetName("r1y2");
        pn.PlaceList.add(p5);

        DataTransfer p6 = new DataTransfer();
        p6.SetName("OP1");
        p6.Value = new TransferOperation("localhost", "1080", "P_TL1");
        pn.PlaceList.add(p6);

        DataTransfer p7 = new DataTransfer();
        p7.SetName("OP2");
        p7.Value = new TransferOperation("localhost", "1080", "P_TL2");
        pn.PlaceList.add(p7);

        DataString in1 = new DataString();
        in1.SetName("in1");
        pn.PlaceList.add(in1);

        DataString in2 = new DataString();
        in2.SetName("in2");
        pn.PlaceList.add(in2);

        //----------------------------iniT------------------------------------
        PetriTransition iniT = new PetriTransition(pn);
        iniT.TransitionName = "iniT";

        Condition iniTCt1 = new Condition(iniT, "ini", TransitionCondition.NotNull);

        GuardMapping grdiniT = new GuardMapping();
        grdiniT.condition= iniTCt1;

        grdiniT.Activations.add(new Activation(iniT, "ini", TransitionOperation.SendOverNetwork, "OP1"));
        grdiniT.Activations.add(new Activation(iniT, "ini", TransitionOperation.SendOverNetwork, "OP2"));
        grdiniT.Activations.add(new Activation(iniT, "", TransitionOperation.MakeNull, "ini"));

        iniT.GuardMappingList.add(grdiniT);

        iniT.Delay = 5;
        pn.Transitions.add(iniT);

        //----------------------------T1------------------------------------
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "T1";
        t1.InputPlaceName.add("r1r2");


        Condition T1Ct1 = new Condition(t1, "r1r2", TransitionCondition.NotNull);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition= T1Ct1;
        grdT1.Activations.add(new Activation(t1, "r1r2", TransitionOperation.Move, "g1r2"));
        grdT1.Activations.add(new Activation(t1, "green", TransitionOperation.SendOverNetwork, "OP1"));
        t1.GuardMappingList.add(grdT1);

        t1.Delay = 5;
        pn.Transitions.add(t1);

        //----------------------------T2------------------------------------
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "T2";
        t2.InputPlaceName.add("g1r2");
        t2.InputPlaceName.add("in1");

        Condition T2Ct1 = new Condition(t2, "g1r2", TransitionCondition.NotNull);
        Condition T2Ct11 = new Condition(t2, "g1r2", TransitionCondition.NotNull);
        Condition T2Ct2 = new Condition(t2, "in1", TransitionCondition.IsNull);
        Condition T2Ct3 = new Condition(t2, "in1", TransitionCondition.NotNull);
        T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);
        T2Ct11.SetNextCondition(LogicConnector.AND, T2Ct3);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition= T2Ct1;
        grdT2.Activations.add(new Activation(t2, "g1r2", TransitionOperation.Move, "y1r2"));
        grdT2.Activations.add(new Activation(t2, "yellow", TransitionOperation.SendOverNetwork, "OP1"));
        grdT2.Activations.add(new Activation(t2, "Five", TransitionOperation.DynamicDelay, ""));
        t2.GuardMappingList.add(grdT2);

        GuardMapping grdT21 = new GuardMapping();
        grdT21.condition= T2Ct11;
        grdT21.Activations.add(new Activation(t2, "g1r2", TransitionOperation.Move, "y1r2"));
        grdT21.Activations.add(new Activation(t2, "yellow", TransitionOperation.SendOverNetwork, "OP1"));
        grdT21.Activations.add(new Activation(t2, "Ten", TransitionOperation.DynamicDelay, ""));
        t2.GuardMappingList.add(grdT21);

        t2.Delay = 5;
        pn.Transitions.add(t2);


        //----------------------------T3------------------------------------
        PetriTransition t3 = new PetriTransition(pn);
        t3.TransitionName = "T3";
        t3.InputPlaceName.add("y1r2");



        Condition T3Ct1 = new Condition(t3, "y1r2", TransitionCondition.NotNull);

        GuardMapping grdT3 = new GuardMapping();
        grdT3.condition= T3Ct1;
        grdT3.Activations.add(new Activation(t3, "y1r2", TransitionOperation.Move, "r1g2"));
        grdT3.Activations.add(new Activation(t3, "red", TransitionOperation.SendOverNetwork, "OP1"));
        grdT3.Activations.add(new Activation(t3, "green", TransitionOperation.SendOverNetwork, "OP2"));

        t3.GuardMappingList.add(grdT3);

        t3.Delay = 5;
        pn.Transitions.add(t3);


        //----------------------------T4------------------------------------
        PetriTransition t4 = new PetriTransition(pn);
        t4.TransitionName = "T4";
        t4.InputPlaceName.add("r1g2");
        t4.InputPlaceName.add("in2");


        Condition T4Ct1 = new Condition(t4, "r1g2", TransitionCondition.NotNull);
        Condition T4Ct11 = new Condition(t2, "r1g2", TransitionCondition.NotNull);
        Condition T4Ct2 = new Condition(t2, "in2", TransitionCondition.IsNull);
        Condition T4Ct3 = new Condition(t2, "in2", TransitionCondition.NotNull);
        T4Ct1.SetNextCondition(LogicConnector.AND, T4Ct2);
        T4Ct11.SetNextCondition(LogicConnector.AND, T4Ct3);

        GuardMapping grdT4 = new GuardMapping();
        grdT4.condition= T4Ct1;
        grdT4.Activations.add(new Activation(t4, "r1g2", TransitionOperation.Move, "r1y2"));
        grdT4.Activations.add(new Activation(t4, "yellow", TransitionOperation.SendOverNetwork, "OP2"));
        grdT4.Activations.add(new Activation(t4, "Five", TransitionOperation.DynamicDelay, ""));
        t4.GuardMappingList.add(grdT4);

        GuardMapping grdT41 = new GuardMapping();
        grdT41.condition= T4Ct11;
        grdT41.Activations.add(new Activation(t4, "r1g2", TransitionOperation.Move, "r1y2"));
        grdT41.Activations.add(new Activation(t4, "yellow", TransitionOperation.SendOverNetwork, "OP2"));
        grdT41.Activations.add(new Activation(t4, "Ten", TransitionOperation.DynamicDelay, ""));
        t4.GuardMappingList.add(grdT41);
        t4.Delay = 5;
        pn.Transitions.add(t4);


        //----------------------------T5------------------------------------
        PetriTransition t5 = new PetriTransition(pn);
        t5.TransitionName = "T5";
        t5.InputPlaceName.add("r1y2");


        Condition T5Ct1 = new Condition(t5, "r1y2", TransitionCondition.NotNull);

        GuardMapping grdT5 = new GuardMapping();
        grdT5.condition= T5Ct1;
        grdT5.Activations.add(new Activation(t5, "r1y2", TransitionOperation.Move, "r1r2"));
        grdT5.Activations.add(new Activation(t5, "red", TransitionOperation.SendOverNetwork, "OP2"));

        t5.GuardMappingList.add(grdT5);

        t5.Delay = 5;
        pn.Transitions.add(t5);

        // -------------------------------------------------------------------------------------
        // ----------------------------PN Start-------------------------------------------------
        // -------------------------------------------------------------------------------------

        System.out.println("Controller 1 started \n ------------------------------");
        pn.Delay = 2000;
        // pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);

    }
}
