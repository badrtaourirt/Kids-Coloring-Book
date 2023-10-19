package com.coloring.page;

import java.util.ArrayList;
import java.util.Random;

public class MyConstant {

    public static int BRUSH_SIZE = 20;
    public static int BRUSH_WIDTH = 50;
    public static int COlORING_BOOK_ID = 0;
    public static int DRAW_COLOR = 0;
    public static int ERASER_WIDTH = 70;
    public static boolean IS_RAINBOW_COLOR_SELECTED = true;
    public static boolean IS_ZOOMCLICKED = false;
    public static int PEN_WIDTH = 8;
    public static int SELECTED_TOOL = 0;
    public static int STROKE_WIDTH = 0;
    public static final int TYPE_BRUSH = 1;
    public static final int TYPE_BRUSH_SIZE1 = 20;
    public static final int TYPE_BRUSH_SIZE2 = 30;
    public static final int TYPE_BRUSH_SIZE3 = 40;
    public static final int TYPE_BUCKET = 0;
    public static final int TYPE_COLOR = 0;
    public static final int TYPE_ERASER = 2;
    public static int TYPE_FILL = 0;
    public static final int TYPE_GLITTER = 2;
    public static final int TYPE_GLOW = 1;
    public static int TYPE_GLOW_PEN = 0;
    public static final int TYPE_GLOW_PEN_DASHED = 3;
    public static final int TYPE_GLOW_PEN_DOTTED = 2;
    public static final int TYPE_GLOW_PEN_PLAIN = 1;
    public static int TYPE_GLOW_RADIUS = 5;
    public static final int TYPE_GLOW_RADIUS1 = 5;
    public static final int TYPE_GLOW_RADIUS2 = 8;
    public static final int TYPE_GLOW_RADIUS3 = 10;
    public static final int TYPE_PATTERN = 1;
    public static final int TYPE_UNiCORN = 0;


//    static ArrayList<Integer> a = getRandomNonRepeatingIntegers(myMoreApps.size(), 0, myMoreApps.size() - 1);
    public static boolean appStartFirstTime = false;
//    static int b = a.get(0).intValue();
//
//    static int g = a.get(5).intValue();
//    static int h = a.get(6).intValue();
//
//    static int i = a.get(7).intValue();
//    static int d = a.get(2).intValue();
//    static int e = a.get(3).intValue();
//    static int c = a.get(1).intValue();
//    static int j = a.get(8).intValue();
//    static int f = a.get(4).intValue();


    public static Integer[] bitmapGlowIds = {Integer.valueOf(R.drawable.black), Integer.valueOf(R.drawable.glow_1), Integer.valueOf(R.drawable.glow_2), Integer.valueOf(R.drawable.glow_3), Integer.valueOf(R.drawable.glow_4), Integer.valueOf(R.drawable.glow_5), Integer.valueOf(R.drawable.glow_6), Integer.valueOf(R.drawable.glow_7), Integer.valueOf(R.drawable.glow_8), Integer.valueOf(R.drawable.glow_9), Integer.valueOf(R.drawable.glow_10), Integer.valueOf(R.drawable.glow_11), Integer.valueOf(R.drawable.glow_12), Integer.valueOf(R.drawable.glow_13), Integer.valueOf(R.drawable.glow_14), Integer.valueOf(R.drawable.glow_15),Integer.valueOf(R.drawable.glow_16), Integer.valueOf(R.drawable.glow_17), Integer.valueOf(R.drawable.glow_18), Integer.valueOf(R.drawable.glow_19),  Integer.valueOf(R.drawable.glow_20)};
    public static Integer[] bitmapUnicornIds = {Integer.valueOf(R.drawable.bitmap12), Integer.valueOf(R.drawable.img1), Integer.valueOf(R.drawable.img2), Integer.valueOf(R.drawable.img3), Integer.valueOf(R.drawable.img4), Integer.valueOf(R.drawable.img5), Integer.valueOf(R.drawable.img6), Integer.valueOf(R.drawable.img7), Integer.valueOf(R.drawable.img8), Integer.valueOf(R.drawable.img9), Integer.valueOf(R.drawable.img10), Integer.valueOf(R.drawable.img11), Integer.valueOf(R.drawable.img12), Integer.valueOf(R.drawable.img13), Integer.valueOf(R.drawable.img14), Integer.valueOf(R.drawable.img15), Integer.valueOf(R.drawable.img16), Integer.valueOf(R.drawable.img17), Integer.valueOf(R.drawable.img18), Integer.valueOf(R.drawable.img19), Integer.valueOf(R.drawable.img20), Integer.valueOf(R.drawable.img21), Integer.valueOf(R.drawable.img22), Integer.valueOf(R.drawable.img23), Integer.valueOf(R.drawable.img24), Integer.valueOf(R.drawable.img25), Integer.valueOf(R.drawable.img26), Integer.valueOf(R.drawable.img27), Integer.valueOf(R.drawable.img28), Integer.valueOf(R.drawable.img30), Integer.valueOf(R.drawable.img31), Integer.valueOf(R.drawable.img32), Integer.valueOf(R.drawable.img33), Integer.valueOf(R.drawable.img34), Integer.valueOf(R.drawable.img35), Integer.valueOf(R.drawable.img36), Integer.valueOf(R.drawable.img37), Integer.valueOf(R.drawable.img38), Integer.valueOf(R.drawable.img39), Integer.valueOf(R.drawable.img40), Integer.valueOf(R.drawable.img41), Integer.valueOf(R.drawable.img42), Integer.valueOf(R.drawable.img43), Integer.valueOf(R.drawable.img44), Integer.valueOf(R.drawable.img45), Integer.valueOf(R.drawable.img46), Integer.valueOf(R.drawable.img47), Integer.valueOf(R.drawable.img48), Integer.valueOf(R.drawable.img49), Integer.valueOf(R.drawable.img50), Integer.valueOf(R.drawable.img51), Integer.valueOf(R.drawable.img52), Integer.valueOf(R.drawable.img53), Integer.valueOf(R.drawable.img54), Integer.valueOf(R.drawable.img55), Integer.valueOf(R.drawable.img59), Integer.valueOf(R.drawable.img60), Integer.valueOf(R.drawable.img61), Integer.valueOf(R.drawable.img62), Integer.valueOf(R.drawable.img63), Integer.valueOf(R.drawable.img64), Integer.valueOf(R.drawable.img65), Integer.valueOf(R.drawable.img66), Integer.valueOf(R.drawable.img67), Integer.valueOf(R.drawable.img68), Integer.valueOf(R.drawable.img69), Integer.valueOf(R.drawable.img70), Integer.valueOf(R.drawable.img71), Integer.valueOf(R.drawable.img72), Integer.valueOf(R.drawable.img73), Integer.valueOf(R.drawable.img74), Integer.valueOf(R.drawable.img75), Integer.valueOf(R.drawable.img76), Integer.valueOf(R.drawable.img77), Integer.valueOf(R.drawable.img78), Integer.valueOf(R.drawable.img79), Integer.valueOf(R.drawable.img80), Integer.valueOf(R.drawable.img81), Integer.valueOf(R.drawable.img82), Integer.valueOf(R.drawable.img83), Integer.valueOf(R.drawable.img84), Integer.valueOf(R.drawable.img85), Integer.valueOf(R.drawable.img86), Integer.valueOf(R.drawable.img87), Integer.valueOf(R.drawable.img88), Integer.valueOf(R.drawable.img89), Integer.valueOf(R.drawable.img90), Integer.valueOf(R.drawable.img91), Integer.valueOf(R.drawable.img92), Integer.valueOf(R.drawable.img93), Integer.valueOf(R.drawable.img94), Integer.valueOf(R.drawable.img95), Integer.valueOf(R.drawable.img96), Integer.valueOf(R.drawable.img97), Integer.valueOf(R.drawable.img98), Integer.valueOf(R.drawable.img99), Integer.valueOf(R.drawable.img100), Integer.valueOf(R.drawable.img101), Integer.valueOf(R.drawable.img102), Integer.valueOf(R.drawable.img103), Integer.valueOf(R.drawable.img104), Integer.valueOf(R.drawable.img105), Integer.valueOf(R.drawable.img106), Integer.valueOf(R.drawable.img107), Integer.valueOf(R.drawable.img108), Integer.valueOf(R.drawable.img109), Integer.valueOf(R.drawable.img110), Integer.valueOf(R.drawable.img111)};

    public static int colorCount = 20;

    public static int drawColor;
    public static int drawHeight = 700;
    public static int drawWidth = 700;

    public static boolean erase;
    public static float eraseR = 50.0f;
    public static int eraseWidth = 50;
    public static float eraseX;
    public static float eraseY;

    public static boolean fromGridActivityColoringBook = false;

    public static int heightInPixels;


    public static boolean isAdmobInterstialLoadFailed;
    public static boolean isBackFromDrawActivity = false;
    public static boolean isBackFromGlow = false;



    public static boolean mute;

    public static int onSizeCalled = 0;

    public static int[] pixels = null;

    public static double screenRatio;
    public static int selectedImageFromBitmap = -1;
    public static int selectedTool;
    public static Integer[] selected_bitmapIds;
    public static int selected_tool = 0;
    public static boolean showNewApp = false;
    public static int strokeWidth;
    public static int widthInPixels;

    static {
//        a.get(9).intValue();
//        myMoreApps.get(a.get(9).intValue()).getLinkName();
    }



    public static int getRandomInt(int i13, int i14) {
        return new Random().nextInt((i14 - i13) + 1) + i13;
    }

    public static ArrayList<Integer> getRandomNonRepeatingIntegers(int i13, int i14, int i15) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (arrayList.size() < i13 - 1) {
            int randomInt = getRandomInt(i14, i15);
            if (!arrayList.contains(Integer.valueOf(randomInt))) {
                arrayList.add(Integer.valueOf(randomInt));
            }
        }
        return arrayList;
    }
}
