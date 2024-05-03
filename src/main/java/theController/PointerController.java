package theController;

import theModel.DataSerialize;
import theView.pointer.Pointer;

public class PointerController {
    private Pointer pointer;
    private DataSerialize dataSerialize;

    public PointerController(Pointer p, DataSerialize d)
    {
        this.pointer = p;
        this.dataSerialize = d;
        System.out.println("PointerController created");
    }
}
