package com.coloring.page;

import static com.coloring.page.CapturePhotoUtils.takeScreenshot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawActivityGlow extends Activity implements View.OnClickListener {
    // unity Ads
    public String interID = "Interstitial_Android";
   // AdmobAds admobAds;

    private static final int BRUSH = 2;
    private static final int ERASER = 3;
    private static final int NEW_PAGE = 5;
    private static final int PAINT = 6;
    private static final int PEN = 1;
    private static final int PEN_DASHED = 8;
    private static final int PEN_DOTTED = 9;
    private static final int SAVE = 4;
    private static final String TAG = "DrawActivity";
    private static final int ZOOM = 7;
    public static DrawActivityGlow drawActivityGlow;
    public static ImageView iv;
    public static Bitmap myart;
    public static int newHeight;
    public static int newWidth;
    ImageView back;
    ImageView dashedPen;
    ImageView dottedPen;

    public DrawerLayout drLayoutBrush;

    public View drawerViewBrush;
    MyApplication myApplication;
    ImageView eraser;
    HorizontalAdapter horizontalAdapter;
    RecyclerView horizontal_recycler_view;

    public FrameLayout horizontal_recycler_view_frameview;

    public boolean isdraweropenedBrush = false;

    public LinearLayout leftTop;

    public int listItemDefaultPos = -1;
    public MyMediaPlayer mediaPlayer;
    com.coloring.page.MediaPlayerSoundAndMusic mediaPlayerSoundAndMusic;

    public DrawingPictureGlow myDrawView;
    ImageView newPage;
    ImageView pen;

    public int rowIndex = -1;
    ImageView save;
    boolean writePermission;

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {
        List<com.coloring.page.Data> a = Collections.emptyList();
        Context b;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView p;
            ImageView q;

            public MyViewHolder(HorizontalAdapter horizontalAdapter, View view) {
                super(view);
                this.p = (ImageView) view.findViewById(R.id.imageview);
                this.q = (ImageView) view.findViewById(R.id.imageviewTick);
            }
        }

        public HorizontalAdapter(List<com.coloring.page.Data> list, Application application) {
            this.a = list;
            this.b = application;
        }

        private void isDefaultPosition(int i) {
            com.coloring.page.MyConstant.IS_RAINBOW_COLOR_SELECTED = i == 28;
            if (i == DrawActivityGlow.this.listItemDefaultPos) {
                DrawActivityGlow.this.brushSelectedOnClickButton();
                if (com.coloring.page.MyConstant.erase) {
                    DrawActivityGlow.this.turnEraserToBrush();
                }
                com.coloring.page.MyConstant.DRAW_COLOR = ContextCompat.getColor(this.b, this.a.get(i).a);
                DrawActivityGlow.this.horizontal_recycler_view_frameview.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
                DrawActivityGlow.this.leftTop.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
            }
        }

        private void setSelectedColorTick(MyViewHolder myViewHolder, int i) {
            if (DrawActivityGlow.this.listItemDefaultPos != -1) {
                if (DrawActivityGlow.this.rowIndex == DrawActivityGlow.this.listItemDefaultPos) {
                    myViewHolder.q.setVisibility(View.VISIBLE);
                    int unused = DrawActivityGlow.this.listItemDefaultPos = -1;
                    return;
                }
            } else if (DrawActivityGlow.this.rowIndex == i) {
                myViewHolder.q.setVisibility(View.VISIBLE);
                return;
            }
            myViewHolder.q.setVisibility(View.INVISIBLE);
        }

        public int getItemCount() {
            return this.a.size();
        }

        public void onBindViewHolder(MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
            myViewHolder.p.setImageResource(this.a.get(i).imageId);
            myViewHolder.p.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    DrawActivityGlow.this.mediaPlayer.playSound(R.raw.click);
                    DrawActivityGlow.this.brushSelectedOnClickButton();
                    int unused = DrawActivityGlow.this.rowIndex = i;
                    com.coloring.page.MyConstant.IS_RAINBOW_COLOR_SELECTED = DrawActivityGlow.this.rowIndex == 28;
                    HorizontalAdapter.this.notifyDataSetChanged();
                    HorizontalAdapter horizontalAdapter = HorizontalAdapter.this;
                    com.coloring.page.MyConstant.DRAW_COLOR = ContextCompat.getColor(horizontalAdapter.b, horizontalAdapter.a.get(i).a);
                    DrawActivityGlow.this.horizontal_recycler_view_frameview.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
                    DrawActivityGlow.this.leftTop.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
                    DrawActivityGlow.this.checkTypeOfPenSelected();
                }
            });
            isDefaultPosition(DrawActivityGlow.this.rowIndex);
            PrintStream printStream = System.err;
            printStream.println("def pos::" + DrawActivityGlow.this.rowIndex);
            setSelectedColorTick(myViewHolder, i);
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vertical_menu, viewGroup, false));
        }
    }

    class paintClass1 implements DialogInterface.OnClickListener {
        final int a;

        paintClass1(int i) {
            this.a = i;
        }

        public void onClick(DialogInterface dialogInterface, int i) {


//            Ad_class.showInterstitial(DrawActivityGlow.this, new Ad_class.onLisoner() {
//
//                public void click() {
//
//
//
//
//                }
//            });
            DrawActivityGlow.this.saveBitmap();
            int i2 = a;
            if (i2 >= 0) {
                DrawActivityGlow.this.insertKidBitmap(i2);
            } else {
                DrawActivityGlow.this.myDrawView.startNew();
            }

        }
    }

    class paintClass2 implements DialogInterface.OnClickListener {
        final int a;

        paintClass2(int i) {
            this.a = i;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            int i2 = this.a;
            if (i2 >= 0) {
                DrawActivityGlow.this.insertKidBitmap(i2);
            } else {
                DrawActivityGlow.this.myDrawView.startNew();
            }
        }
    }


    public void brushSelectedOnClickButton() {
        if (com.coloring.page.MyConstant.SELECTED_TOOL == 2) {
            menuSelectedClick(2);
            com.coloring.page.MyConstant.SELECTED_TOOL = 1;
            com.coloring.page.MyConstant.strokeWidth = com.coloring.page.MyConstant.BRUSH_SIZE;
            com.coloring.page.MyConstant.erase = false;
        }
    }


    public void checkTypeOfPenSelected() {
        if (com.coloring.page.MyConstant.TYPE_GLOW_PEN == 1) {
            menuSelectedClick(1);
        }
        if (com.coloring.page.MyConstant.TYPE_GLOW_PEN == 3) {
            menuSelectedClick(8);
        }
        if (com.coloring.page.MyConstant.TYPE_GLOW_PEN == 2) {
            menuSelectedClick(9);
        }
    }

    private void chooseDrawingType() {
        com.coloring.page.MyConstant.selected_bitmapIds = com.coloring.page.MyConstant.bitmapGlowIds;
    }

    private void closeAllDrawer() {
        this.drLayoutBrush.closeDrawer(this.drawerViewBrush);
        this.isdraweropenedBrush = false;
    }


    public void disableBrushDrawer(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.GONE);
        drawerLayout.setFocusable(false);
        drawerLayout.setClickable(false);
        drawerLayout.setEnabled(false);
    }

    private void drawerImplementationForBrush() {
        this.drLayoutBrush = (DrawerLayout) findViewById(R.id.dr_layout_brush);
        this.drawerViewBrush = findViewById(R.id.drawer_brush);
        this.drLayoutBrush.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                DrawActivityGlow.this.drLayoutBrush.closeDrawer(DrawActivityGlow.this.drawerViewBrush);
                DrawActivityGlow.this.isdraweropenedBrush = false;
                return true;
            }
        });
        View findViewById = findViewById(R.id.ivBrushSize1);
        View findViewById2 = findViewById(R.id.ivBrushSize2);
        View findViewById3 = findViewById(R.id.ivBrushSize3);
        this.drLayoutBrush.closeDrawer(this.drawerViewBrush);
        this.drLayoutBrush.openDrawer(this.drawerViewBrush);
        this.isdraweropenedBrush = false;
        findViewById.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DrawActivityGlow drawActivityGlow = DrawActivityGlow.this;
                drawActivityGlow.setBrushClick(drawActivityGlow.drLayoutBrush, DrawActivityGlow.this.drawerViewBrush, 20, 5);
            }
        });
        findViewById2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DrawActivityGlow drawActivityGlow = DrawActivityGlow.this;
                drawActivityGlow.setBrushClick(drawActivityGlow.drLayoutBrush, DrawActivityGlow.this.drawerViewBrush, 30, 8);
            }
        });
        findViewById3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DrawActivityGlow drawActivityGlow = DrawActivityGlow.this;
                drawActivityGlow.setBrushClick(drawActivityGlow.drLayoutBrush, DrawActivityGlow.this.drawerViewBrush, 40, 10);
            }
        });
        this.drLayoutBrush.addDrawerListener(new ActionBarDrawerToggle(this, this.drLayoutBrush, R.drawable.back_1, R.string.app_name, R.string.black_bat) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                DrawActivityGlow.this.mediaPlayer.playSound(R.raw.drawer_close);
                DrawActivityGlow drawActivityGlow = DrawActivityGlow.this;
                drawActivityGlow.disableBrushDrawer(drawActivityGlow.drLayoutBrush);
            }

            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                DrawActivityGlow.this.mediaPlayer.playSound(R.raw.drawer);
                DrawActivityGlow drawActivityGlow = DrawActivityGlow.this;
                drawActivityGlow.enableBrushDrawer(drawActivityGlow.drLayoutBrush);
            }
        });
    }


    public void enableBrushDrawer(DrawerLayout drawerLayout) {
        this.drLayoutBrush.openDrawer(this.drawerViewBrush);
        drawerLayout.setVisibility(View.VISIBLE);
        drawerLayout.setFocusable(true);
        drawerLayout.setClickable(true);
        drawerLayout.setEnabled(true);

    }

    private void findByViewIds() {
        this.horizontal_recycler_view = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        this.horizontal_recycler_view_frameview = (FrameLayout) findViewById(R.id.horizontal_recycler_view_frameview);
        this.leftTop = (LinearLayout) findViewById(R.id.leftTop);
        this.myDrawView = (DrawingPictureGlow) findViewById(R.id.draw);
        this.pen = (ImageView) findViewById(R.id.pen);
        this.dottedPen = (ImageView) findViewById(R.id.dotted_pen);
        this.dashedPen = (ImageView) findViewById(R.id.dashed_pen);
        this.back = (ImageView) findViewById(R.id.back);
        this.eraser = (ImageView) findViewById(R.id.eraser);
        this.save = (ImageView) findViewById(R.id.save);
        this.newPage = (ImageView) findViewById(R.id.newPage);
        iv = (ImageView) findViewById(R.id.iv);
        this.pen.setOnClickListener(this);
        this.dottedPen.setOnClickListener(this);
        this.dashedPen.setOnClickListener(this);
        this.eraser.setOnClickListener(this);
        this.save.setOnClickListener(this);
        this.back.setOnClickListener(this);
        this.newPage.setOnClickListener(this);
    }

    private void finishActivity() {
        try{
//        if (com.coloring.page.CapturePhotoUtils.file.getAbsolutePath() != null) {
            com.coloring.page.MyConstant.isBackFromDrawActivity = true;
            this.mediaPlayer.StopMp();
            this.mediaPlayer.playSound(R.raw.click);
            Intent intent = new Intent(this, com.coloring.page.ShareUrArtGlow.class);
            intent.putExtra("BitmapImage", com.coloring.page.CapturePhotoUtils.file.getAbsolutePath());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            return;
//        }
        }catch (Exception e){



        }
//        finishActivityNoSave();
    }


    public void finishActivityNoSave() {
        com.coloring.page.MyConstant.isBackFromDrawActivity = true;
        this.mediaPlayer.StopMp();
        this.mediaPlayer.playSound(R.raw.click);
        finish();
        MyConstant.COlORING_BOOK_ID = 1;
        startActivity(new Intent(this,GridActivityColoringBookGlow.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public static DrawActivityGlow getDrawActivity() {
        return drawActivityGlow;
    }

    private void hideNavigation() {
        View decorView;
        int i;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 14) {
            decorView = getWindow().getDecorView();
            i = 7686;
        } else if (i2 >= 11) {
            decorView = getWindow().getDecorView();
            i = 1;
        } else {
            return;
        }
        decorView.setSystemUiVisibility(i);
    }

    private void initialize() {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        com.coloring.page.MyConstant.heightInPixels = defaultDisplay.getHeight();
        com.coloring.page.MyConstant.widthInPixels = defaultDisplay.getWidth();
        double d = (double) com.coloring.page.MyConstant.heightInPixels;
        double d2 = (double) com.coloring.page.MyConstant.widthInPixels;
        Double.isNaN(d);
        Double.isNaN(d2);
        com.coloring.page.MyConstant.screenRatio = d / d2;
        com.coloring.page.MyConstant.erase = false;
        com.coloring.page.MyConstant.SELECTED_TOOL = -1;
        com.coloring.page.MyConstant.TYPE_GLOW_PEN = 1;
        com.coloring.page.MyConstant.TYPE_GLOW_RADIUS = 5;
        this.writePermission = false;
    }

    private void initializeMediaPlayer() {
        this.mediaPlayerSoundAndMusic = new com.coloring.page.MediaPlayerSoundAndMusic();
        this.mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.welcomemain);
        this.mediaPlayer = new MyMediaPlayer(this);
        this.mediaPlayer.playSelectArtRandomSound();
    }

    private void initializeOnSizeChangedValue() {
        com.coloring.page.MyConstant.onSizeCalled = 0;
    }

    private void menuSelectedClick(int i) {
        ImageView imageView;
        int i2 = R.drawable.glow_pen_plain;
        switch (i) {
            case 1:
                this.mediaPlayer.playSound(R.raw.click);
                imageView = this.pen;
                i2 = R.drawable.glow_pen_plain_sel;
                imageView.setImageResource(i2);
                this.eraser.setImageResource(R.drawable.menu4eraser);
                this.dottedPen.setImageResource(R.drawable.glow_pen_dotted);
                break;
            case 2:
            case 7:
                this.mediaPlayer.playSound(R.raw.click);
                imageView = this.pen;
                imageView.setImageResource(i2);
                this.eraser.setImageResource(R.drawable.menu4eraser);
                this.dottedPen.setImageResource(R.drawable.glow_pen_dotted);
                break;
            case 3:
                this.mediaPlayer.playSound(R.raw.click);
                this.pen.setImageResource(R.drawable.glow_pen_plain);
                this.eraser.setImageResource(R.drawable.menu4eraser_sel);
                this.dottedPen.setImageResource(R.drawable.glow_pen_dotted);
                break;
            case 4:
            case 5:
                this.mediaPlayer.playSound(R.raw.click);
                return;
            case 6:
            default:
                return;
            case 8:
                this.mediaPlayer.playSound(R.raw.click);
                this.pen.setImageResource(R.drawable.glow_pen_plain);
                this.eraser.setImageResource(R.drawable.menu4eraser);
                this.dottedPen.setImageResource(R.drawable.glow_pen_dotted);
                this.dashedPen.setImageResource(R.drawable.glow_pen_dashed_sel);
                return;
            case 9:
                this.mediaPlayer.playSound(R.raw.click);
                this.pen.setImageResource(R.drawable.glow_pen_plain);
                this.eraser.setImageResource(R.drawable.menu4eraser);
                this.dottedPen.setImageResource(R.drawable.glow_pen_dotted_sel);
                break;
        }
        this.dashedPen.setImageResource(R.drawable.glow_pen_dashed);
    }


    private void refreshData(List<com.coloring.page.Data> list) {
        this.horizontalAdapter = new HorizontalAdapter(list, getApplication());
        this.horizontal_recycler_view.setAdapter(this.horizontalAdapter);
        this.listItemDefaultPos = this.horizontalAdapter.getItemCount() - 1;
        this.rowIndex = this.listItemDefaultPos;
        this.horizontalAdapter.notifyDataSetChanged();
    }


    public void saveBitmap() {
        requestPermissionWrite();
        if (this.writePermission) {
            this.myDrawView.setDrawingCacheEnabled(true);
            try {
//                com.coloring.page.CapturePhotoUtils.insertImage(getContentResolver(), this.myDrawView.getDrawingCache(), "drawing", "storage");

              takeScreenshot(this,this.myDrawView);

                this.mediaPlayer.playSound(R.raw.camera_click);
            } catch (Exception unused) {
            }
            this.myDrawView.destroyDrawingCache();
        }
    }


    public void saveBitmapOnBackPressed() {
        requestPermissionWrite();
        if (this.writePermission) {
            this.myDrawView.setDrawingCacheEnabled(true);
            try {
//                com.coloring.page.CapturePhotoUtils.file=new File(com.coloring.page.CapturePhotoUtils.insertImage(getContentResolver(), this.myDrawView.getDrawingCache(), "drawing", "storage"));

                takeScreenshot(this,this.myDrawView);
                this.mediaPlayer.playSound(R.raw.camera_click);
                myart = this.myDrawView.getDrawingCache();
            } catch (Exception unused) {
            }
            finishActivity();
        }
    }

    private void setBottomColorLayout(List<com.coloring.page.Data> list) {
        this.horizontalAdapter = new HorizontalAdapter(list, getApplication());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        this.horizontal_recycler_view.setLayoutManager(linearLayoutManager);
        this.horizontal_recycler_view.setAdapter(this.horizontalAdapter);
        linearLayoutManager.setStackFromEnd(true);
    }


    public void setBrushClick(DrawerLayout drawerLayout, View view, int i, int i2) {
        com.coloring.page.MyConstant.SELECTED_TOOL = 1;
        com.coloring.page.MyConstant.TYPE_GLOW_RADIUS = i2;
        this.mediaPlayer.playSound(R.raw.select);
        drawerLayout.closeDrawer(view);
        this.isdraweropenedBrush = false;
        com.coloring.page.MyConstant.BRUSH_SIZE = i;
        com.coloring.page.MyConstant.erase = false;
    }

    private void setDefaultColor() {
        com.coloring.page.MyConstant.DRAW_COLOR = ContextCompat.getColor(this, R.color.color2);
        this.horizontal_recycler_view_frameview.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
        this.leftTop.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
        turnEraserToBrush();
        this.rowIndex = 28;
        this.horizontalAdapter.notifyDataSetChanged();
    }

    public void createNewPageDialog(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.new_page_question).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e("dsd","");
            }
        }).setNeutralButton(R.string.no, new paintClass2(i)).setPositiveButton(R.string.yes, new paintClass1(i));
        builder.create().show();
    }

    public void insertBitmap() {
        if (com.coloring.page.MyConstant.selectedImageFromBitmap > -1 && com.coloring.page.MyConstant.fromGridActivityColoringBook) {
            com.coloring.page.MyConstant.fromGridActivityColoringBook = false;
            insertKidBitmap(com.coloring.page.MyConstant.selectedImageFromBitmap);
        }
    }

    public void insertKidBitmap(int i) {
        int i2;
        int intValue = com.coloring.page.MyConstant.selected_bitmapIds[i].intValue();
        this.myDrawView.kidBitmap = BitmapFactory.decodeResource(getResources(), intValue);
        double d = (double) com.coloring.page.MyConstant.drawHeight;
        double height = (double) this.myDrawView.kidBitmap.getHeight();
        Double.isNaN(d);
        Double.isNaN(height);
        double d2 = d / height;
        double d3 = (double) com.coloring.page.MyConstant.drawWidth;
        double width = (double) this.myDrawView.kidBitmap.getWidth();
        Double.isNaN(d3);
        Double.isNaN(width);
        double d4 = d3 / width;
        double width2 = (double) this.myDrawView.kidBitmap.getWidth();
        Double.isNaN(width2);
        newWidth = (int) (width2 * d4);
        double height2 = (double) this.myDrawView.kidBitmap.getHeight();
        Double.isNaN(height2);
        newHeight = (int) (height2 * d2);
        int i3 = newHeight;
        if (i3 > 0 && (i2 = newWidth) > 0) {
            DrawingPictureGlow drawingPictureGlow = this.myDrawView;
            drawingPictureGlow.kidBitmap = Bitmap.createScaledBitmap(drawingPictureGlow.kidBitmap, i2, i3, true);
            if (com.coloring.page.MyConstant.selectedTool == -1) {
                com.coloring.page.MyConstant.selectedTool = 1;
            }
        }
        com.coloring.page.MyConstant.SELECTED_TOOL = 0;
        com.coloring.page.MyConstant.strokeWidth = 0;
        com.coloring.page.MyConstant.erase = false;
    }




    @Override
    public void onBackPressed() {
        com.coloring.page.MyConstant.isBackFromGlow = true;
        savePageDialogOnBackPressed();
    }
    private void NextActivity2() {
        savePageDialogOnBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                MyConstant.isBackFromGlow = true;
                closeAllDrawer();
             //   shoInter();
                savePageDialogOnBackPressed();
              //  AdmobAds.showInterAdmob(this::NextActivity2);
                return;
            case R.id.dashed_pen:
                MyConstant.TYPE_GLOW_PEN = 3;
                MyConstant.SELECTED_TOOL = 1;
                MyConstant.erase = false;
                menuSelectedClick(8);
                enableBrushDrawer(this.drLayoutBrush);
                break;
            case R.id.dotted_pen:
                MyConstant.TYPE_GLOW_PEN = 2;
                MyConstant.SELECTED_TOOL = 1;
                MyConstant.erase = false;
                menuSelectedClick(9);
                enableBrushDrawer(this.drLayoutBrush);
                break;
            case R.id.eraser:
                menuSelectedClick(3);
                MyConstant.SELECTED_TOOL = 2;
                MyConstant.strokeWidth = MyConstant.eraseWidth;
                MyConstant.erase = true;
                closeAllDrawer();
                return;
            case R.id.newPage:
                menuSelectedClick(5);
                createNewPageDialog(-1);
                setDefaultColor();
                closeAllDrawer();
                return;
            case R.id.pen:
                MyConstant.TYPE_GLOW_PEN = 1;
                MyConstant.SELECTED_TOOL = 1;
                MyConstant.erase = false;
                menuSelectedClick(1);
                enableBrushDrawer(this.drLayoutBrush);
                break;
            case R.id.save:
                menuSelectedClick(4);
                savePageDialog();
                closeAllDrawer();
                return;
            default:
                return;
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        System.err.print("glow 1");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        drawActivityGlow = this;
        initialize();
        initializeOnSizeChangedValue();
        initializeMediaPlayer();
        setContentView(R.layout.activity_draw_glow);
        findByViewIds();
        setBottomColorLayout(setBottomColorsList());
        drawerImplementationForBrush();
        chooseDrawingType();
        setDefaultColor();
        checkTypeOfPenSelected();
        MyApplication.setContext(this);

       // admobAds = new AdmobAds(this);
      //  AdmobAds.loadInterAdmob();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Bitmap bitmap = DrawingPictureGlow.canvasBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            DrawingPictureGlow.canvasBitmap = null;
        }
        Bitmap bitmap2 = this.myDrawView.kidBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
        //Ad_class.loadAd(DrawActivityGlow.this);
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 1) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                this.writePermission = false;
                boolean shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE");
                return;
            }
            this.writePermission = true;
            saveBitmap();
        }
    }


    @Override
    public void onResume() {
        hideNavigation();
        disableBrushDrawer(this.drLayoutBrush);
        this.mediaPlayerSoundAndMusic.startMainMusic();
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
    }

    @Override
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            hideNavigation();
        }
    }

    public void requestPermissionWrite() {
        if (Build.VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission is granted");
            this.writePermission = true;
            return;
        }
        Log.v(TAG, "Permission is revoked");
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
    }

    public void savePageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.store_picture_title).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e("dsd","");
            }
        }).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

//                Ad_class.showInterstitial(DrawActivityGlow.this, new Ad_class.onLisoner() {
//
//                    public void click() {
//
//                    }
//                });
                DrawActivityGlow.this.saveBitmap();

            }
        });
        builder.create().show();
    }

    public void savePageDialogOnBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.store_picture_title).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                DrawActivityGlow.this.finishActivityNoSave();
            }
        }).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                DrawActivityGlow.this.saveBitmapOnBackPressed();
            }
        });
        builder.create().show();
    }

    public List<com.coloring.page.Data> setBottomColorsList() {
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        arrayList.add(new com.coloring.page.Data(1, R.drawable.c10_white, "Image 2", R.color.color25));
        arrayList.add(new com.coloring.page.Data(2, R.drawable.c9_grey2, "Image 1", R.color.color24));
        arrayList.add(new com.coloring.page.Data(3, R.drawable.c8_brown2, "Image 2", R.color.color23));
        arrayList.add(new com.coloring.page.Data(4, R.drawable.c8_brown1, "Image 1", R.color.color22));
        arrayList.add(new com.coloring.page.Data(5, R.drawable.c8_brown0, "Image 1", R.color.color220));
        arrayList.add(new com.coloring.page.Data(6, R.drawable.c7_pink3, "Image 3", R.color.color21));
        arrayList.add(new com.coloring.page.Data(7, R.drawable.c7_pink2, "Image 2", R.color.color20));
        arrayList.add(new com.coloring.page.Data(8, R.drawable.c7_pink1, "Image 1", R.color.color19));
        arrayList.add(new com.coloring.page.Data(9, R.drawable.c6_purple3, "Image 3", R.color.color18));
        arrayList.add(new com.coloring.page.Data(10, R.drawable.c6_purple2, "Image 2", R.color.color17));
        arrayList.add(new com.coloring.page.Data(11, R.drawable.c6_purple1, "Image 1", R.color.color16));
        arrayList.add(new com.coloring.page.Data(12, R.drawable.c5_blue4, "Image 3", R.color.color15));
        arrayList.add(new com.coloring.page.Data(13, R.drawable.c5_blue3, "Image 2", R.color.color14));
        arrayList.add(new com.coloring.page.Data(14, R.drawable.c5_blue2, "Image 1", R.color.color13));
        arrayList.add(new com.coloring.page.Data(15, R.drawable.c5_blue1, "Image 3", R.color.color12));
        arrayList.add(new com.coloring.page.Data(16, R.drawable.c5_blue0, "Image 3", R.color.color120));
        arrayList.add(new com.coloring.page.Data(17, R.drawable.c4_green4, "Image 2", R.color.color11));
        arrayList.add(new com.coloring.page.Data(18, R.drawable.c4_green3, "Image 1", R.color.color10));
        arrayList.add(new com.coloring.page.Data(19, R.drawable.c4_green2, "Image 3", R.color.color9));
        arrayList.add(new com.coloring.page.Data(20, R.drawable.c4_green1, "Image 2", R.color.color8));
        arrayList.add(new com.coloring.page.Data(21, R.drawable.c4_green0, "Image 2", R.color.color80));
        arrayList.add(new com.coloring.page.Data(22, R.drawable.c3_yellow2, "Image 1", R.color.color7));
        arrayList.add(new com.coloring.page.Data(23, R.drawable.c3_yellow1, "Image 3", R.color.color6));
        arrayList.add(new com.coloring.page.Data(24, R.drawable.c3_yellow0, "Image 3", R.color.color60));
        arrayList.add(new com.coloring.page.Data(25, R.drawable.c2_orange2, "Image 2", R.color.color5));
        arrayList.add(new com.coloring.page.Data(26, R.drawable.c2_orange1, "Image 1", R.color.color4));
        arrayList.add(new com.coloring.page.Data(27, R.drawable.c1_red3, "Image 3", R.color.color3));
        arrayList.add(new com.coloring.page.Data(28, R.drawable.c1_red2, "Image 2", R.color.color2));
        arrayList.add(new com.coloring.page.Data(29, R.drawable.rainbow_color, "Image 2", R.color.color2));
        return arrayList;
    }

    public List<com.coloring.page.Data> setBottomGlitterList() {
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        arrayList.add(new com.coloring.page.Data(1, R.drawable.git_1, "g_1", R.color.color25));
        arrayList.add(new com.coloring.page.Data(2, R.drawable.git_2, "g_2", R.color.color24));
        arrayList.add(new com.coloring.page.Data(3, R.drawable.git_3, "g_3", R.color.color23));
        arrayList.add(new com.coloring.page.Data(4, R.drawable.git_4, "g_4", R.color.color22));
        arrayList.add(new com.coloring.page.Data(5, R.drawable.git_5, "g_5", R.color.color21));
        arrayList.add(new com.coloring.page.Data(6, R.drawable.git_6, "g_6", R.color.color20));
        arrayList.add(new com.coloring.page.Data(7, R.drawable.git_7, "g_7", R.color.color19));
        arrayList.add(new com.coloring.page.Data(8, R.drawable.git_8, "g_8", R.color.color18));
        arrayList.add(new com.coloring.page.Data(9, R.drawable.git_9, "g_9", R.color.color17));
        arrayList.add(new com.coloring.page.Data(10, R.drawable.git_10, "g_10", R.color.color16));
        arrayList.add(new com.coloring.page.Data(11, R.drawable.git_11, "g_11", R.color.color15));
        arrayList.add(new com.coloring.page.Data(12, R.drawable.git_12, "g_12", R.color.color14));
        return arrayList;
    }

    public List<com.coloring.page.Data> setBottomPatternList() {
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        arrayList.add(new com.coloring.page.Data(1, R.drawable.pat_1, "patt_1", R.color.color25));
        arrayList.add(new com.coloring.page.Data(2, R.drawable.pat_2, "patt_2", R.color.color24));
        arrayList.add(new com.coloring.page.Data(3, R.drawable.pat_3, "patt_3", R.color.color23));
        arrayList.add(new com.coloring.page.Data(4, R.drawable.pat_4, "patt_4", R.color.color22));
        arrayList.add(new com.coloring.page.Data(5, R.drawable.pat_5, "patt_5", R.color.color21));
        arrayList.add(new com.coloring.page.Data(6, R.drawable.pat_6, "patt_6", R.color.color20));
        arrayList.add(new com.coloring.page.Data(7, R.drawable.pat_7, "patt_7", R.color.color19));
        arrayList.add(new com.coloring.page.Data(8, R.drawable.pat_8, "patt_8", R.color.color18));
        arrayList.add(new com.coloring.page.Data(9, R.drawable.pat_9, "patt_9", R.color.color17));
        arrayList.add(new com.coloring.page.Data(10, R.drawable.pat_10, "patt_10", R.color.color16));
        arrayList.add(new com.coloring.page.Data(11, R.drawable.pat_11, "patt_11", R.color.color15));
        arrayList.add(new com.coloring.page.Data(12, R.drawable.pat_12, "patt_12", R.color.color14));
        arrayList.add(new com.coloring.page.Data(13, R.drawable.pat_13, "patt_13", R.color.color13));
        arrayList.add(new com.coloring.page.Data(14, R.drawable.pat_14, "patt_14", R.color.color12));
        arrayList.add(new com.coloring.page.Data(15, R.drawable.pat_15, "patt_15", R.color.color11));
        arrayList.add(new com.coloring.page.Data(16, R.drawable.pat_16, "patt_16", R.color.color10));
        arrayList.add(new com.coloring.page.Data(17, R.drawable.pat_17, "patt_17", R.color.color9));
        arrayList.add(new com.coloring.page.Data(18, R.drawable.pat_18, "patt_18", R.color.color9));
        return arrayList;
    }

    public void turnEraserToBrush() {
        com.coloring.page.MyConstant.SELECTED_TOOL = 1;
        com.coloring.page.MyConstant.strokeWidth = com.coloring.page.MyConstant.BRUSH_SIZE;
        com.coloring.page.MyConstant.erase = false;
    }
}
