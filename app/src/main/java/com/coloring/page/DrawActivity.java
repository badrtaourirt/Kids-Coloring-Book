package com.coloring.page;

import static com.coloring.page.CapturePhotoUtils.takeScreenshot;
import static com.coloring.page.Facebook.rewardedVideoAd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawActivity extends Activity implements View.OnClickListener {

    public String interID = "Interstitial_Android";


    private static final int BRUSH = 2;
    private static final int ERASER = 3;
    private static final int NEW_PAGE = 5;
    private static final int PAINT = 6;
    private static final int PEN = 1;
    private static final int SAVE = 4;
    private static final String TAG = "DrawActivity";
    private static final int ZOOM = 7;
    public static DrawActivity drawActivity = null;
    public static boolean ispatternClicked = false;
    public static ImageView iv;
    public static Bitmap myart;
    public static int newHeight;
    public static int newWidth;
    protected DrawerLayout a;
    protected DrawerLayout b;
    private ImageView back;
    private ImageView choose_colortype;

    public View drawerViewBrush;

    public View drawerViewColor;
    private ImageView eraser;
    MyApplication myApplication;
    private HorizontalAdapter horizontalAdapter;
    private RecyclerView horizontal_recycler_view;

    public FrameLayout horizontal_recycler_view_frameview;

    public boolean isdraweropenedBrush = false;

    public boolean isdraweropenedColor = false;

    public LinearLayout leftTop;

    public int listItemDefaultPos = -1;
    private ImageView mPaint;
    public MyMediaPlayer mediaPlayer;
    private com.coloring.page.MediaPlayerSoundAndMusic mediaPlayerSoundAndMusic;

    public com.coloring.page.DrawingPicture myDrawView;
    private ImageView newPage;
    private ImageView pen;

    public int row_index = -1;
    private ImageView save;
    private boolean writePermission;

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
            if (i == DrawActivity.this.listItemDefaultPos) {
                DrawActivity.this.brushSelectedOnClickButton();
                if (com.coloring.page.MyConstant.erase) {
                    DrawActivity.this.turnEraserToBrush();
                }
                if (DrawActivity.ispatternClicked) {
                    DrawActivity.this.myDrawView.setPattern(this.a.get(i).getTxt());
                    return;
                }
                com.coloring.page.MyConstant.DRAW_COLOR = ContextCompat.getColor(this.b, this.a.get(i).a);
                DrawActivity.this.myDrawView.setPathColor(com.coloring.page.MyConstant.DRAW_COLOR);
                DrawActivity.this.horizontal_recycler_view_frameview.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
                DrawActivity.this.leftTop.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
            }
        }

        private void setSelectedColorTick(MyViewHolder myViewHolder, int i) {
            if (DrawActivity.this.listItemDefaultPos != -1) {
                if (DrawActivity.this.row_index == DrawActivity.this.listItemDefaultPos) {
                    myViewHolder.q.setVisibility(View.VISIBLE);
                    int unused = DrawActivity.this.listItemDefaultPos = -1;
                    return;
                }
            } else if (DrawActivity.this.row_index == i) {
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
                    DrawActivity.this.mediaPlayer.playSound(R.raw.click);
                    DrawActivity.this.brushSelectedOnClickButton();
                    int unused = DrawActivity.this.row_index = i;
                    HorizontalAdapter.this.notifyDataSetChanged();
                    if (DrawActivity.ispatternClicked) {
                        DrawActivity.this.myDrawView.setPattern(HorizontalAdapter.this.a.get(i).getTxt());
                        return;
                    }
                    HorizontalAdapter horizontalAdapter = HorizontalAdapter.this;
                    com.coloring.page.MyConstant.DRAW_COLOR = ContextCompat.getColor(horizontalAdapter.b, horizontalAdapter.a.get(i).a);
                    DrawActivity.this.myDrawView.setPathColor(com.coloring.page.MyConstant.DRAW_COLOR);
                    DrawActivity.this.horizontal_recycler_view_frameview.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
                    DrawActivity.this.leftTop.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
                }
            });
            isDefaultPosition(DrawActivity.this.row_index);
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


//            Ad_class.showInterstitial(DrawActivity.this, new Ad_class.onLisoner() {
//
//                public void click() {
//
//
//
//                }
//            });
            DrawActivity.this.saveBitmap();

            int i2 = a;
            if (i2 >= 0) {
                DrawActivity.this.insertKidBitmap(i2);
            } else {
                DrawActivity.this.myDrawView.startNew();
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
                DrawActivity.this.insertKidBitmap(i2);
            } else {
                DrawActivity.this.myDrawView.startNew();
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
        if (com.coloring.page.MyConstant.SELECTED_TOOL == 0 && ispatternClicked) {
            menuSelectedClick(2);
            com.coloring.page.MyConstant.SELECTED_TOOL = 1;
            com.coloring.page.MyConstant.strokeWidth = com.coloring.page.MyConstant.BRUSH_SIZE;
            com.coloring.page.MyConstant.erase = false;
        }
    }

    private void closeAllDrawer() {
        this.b.closeDrawer(this.drawerViewColor);
        this.a.closeDrawer(this.drawerViewBrush);
        this.isdraweropenedColor = false;
        this.isdraweropenedBrush = false;
    }


    public void enableColorDrawer(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.VISIBLE);
        drawerLayout.setFocusable(true);
        drawerLayout.setClickable(true);
        drawerLayout.setEnabled(true);
    }

    private void finishActivity() {

            com.coloring.page.MyConstant.isBackFromDrawActivity = true;
            this.mediaPlayer.StopMp();
            this.mediaPlayer.playSound(R.raw.click);
            Intent intent = new Intent(this, com.coloring.page.ShareUrArt.class);
            intent.putExtra("BitmapImage", com.coloring.page.CapturePhotoUtils.file.getAbsolutePath());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            return;

    }


    public void finishActivityNoSave() {
        com.coloring.page.MyConstant.isBackFromDrawActivity = true;
        this.mediaPlayer.StopMp();
        this.mediaPlayer.playSound(R.raw.click);
        finish();
        startActivity(new Intent(this, com.coloring.page.GridActivityColoringBook.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public static DrawActivity getDrawActivity() {
        return drawActivity;
    }

    private void isBrushSelected() {
        int i = com.coloring.page.MyConstant.TYPE_FILL;
        if (i == 1 || i == 2) {
            this.pen.setImageResource(R.drawable.menu1pencil_sel);
            this.eraser.setImageResource(R.drawable.menu4eraser);
            this.mPaint.setImageResource(R.drawable.menu3bucket);
        }
    }

    ImageView imageView;

    private void menuSelectedClick(int i) {

        int i2 = R.drawable.menu1pencil;
        switch (i) {
            case 1:
                this.mediaPlayer.playSound(R.raw.click);
                imageView = this.pen;
                i2 = R.drawable.menu1pencil_sel;
                break;
            case 2:
            case 7:
                this.mediaPlayer.playSound(R.raw.click);
                imageView = this.pen;
                break;
            case 3:
                this.mediaPlayer.playSound(R.raw.click);
                this.pen.setImageResource(R.drawable.menu1pencil);
                this.eraser.setImageResource(R.drawable.menu4eraser_sel);
                break;
            case 4:
            case 5:
                this.mediaPlayer.playSound(R.raw.click);
                return;
            case 6:
                this.mediaPlayer.playSound(R.raw.click);
                this.pen.setImageResource(R.drawable.menu1pencil);
                this.eraser.setImageResource(R.drawable.menu4eraser);
                this.mPaint.setImageResource(R.drawable.menu3bucket_sel);
                ispatternClicked = false;
                refreshData(setBottomColorsList());
                return;
            default:
                return;
        }
        imageView.setImageResource(i2);
        this.eraser.setImageResource(R.drawable.menu4eraser);
        this.mPaint.setImageResource(R.drawable.menu3bucket);
    }

    private void refreshData(List<com.coloring.page.Data> list) {
        this.horizontalAdapter = new HorizontalAdapter(list, getApplication());
        this.horizontal_recycler_view.setAdapter(this.horizontalAdapter);
        this.listItemDefaultPos = this.horizontalAdapter.getItemCount() - 1;
        this.row_index = this.listItemDefaultPos;
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

        }
    }


    public void saveBitmapOnBackPressed() {
        requestPermissionWrite();
        if (this.writePermission) {
            this.myDrawView.setDrawingCacheEnabled(true);
            try {
//                com.coloring.page.CapturePhotoUtils.file=new File( com.coloring.page.CapturePhotoUtils.insertImage(getContentResolver(), this.myDrawView.getDrawingCache(), "drawing", "storage"));

                takeScreenshot(this,this.myDrawView);
                this.mediaPlayer.playSound(R.raw.camera_click);
                myart = this.myDrawView.getDrawingCache();
            } catch (Exception unused) {
            }
            finishActivity();
        }
    }


    public void setBrushClick(DrawerLayout drawerLayout, View view, int i) {
        com.coloring.page.MyConstant.SELECTED_TOOL = 1;
        drawerLayout.closeDrawer(view);
        this.mediaPlayer.playSound(R.raw.select);
        this.isdraweropenedBrush = false;
        com.coloring.page.MyConstant.BRUSH_SIZE = i;
        com.coloring.page.MyConstant.erase = false;
    }


    public void setFillType(DrawerLayout drawerLayout, View view, int i, boolean z) {
        ispatternClicked = false;
        drawerLayout.closeDrawer(view);
        com.coloring.page.MyConstant.TYPE_FILL = i;
        this.isdraweropenedColor = false;
        ispatternClicked = z;
        refreshData(a(com.coloring.page.MyConstant.TYPE_FILL));
        isBrushSelected();
    }


    public List<com.coloring.page.Data> a(int i) {
        return i != 0 ? i != 1 ? i != 2 ? setBottomColorsList() : setBottomGlitterList() : setBottomPatternList() : setBottomColorsList();
    }


    public void a() {
        int i = com.coloring.page.MyConstant.COlORING_BOOK_ID;
        if (i == 0) {
            com.coloring.page.MyConstant.selected_bitmapIds = com.coloring.page.MyConstant.bitmapUnicornIds;
        } else if (i == 1) {
            com.coloring.page.MyConstant.selected_bitmapIds = com.coloring.page.MyConstant.bitmapGlowIds;
        }
    }


    public void a(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.GONE);
        drawerLayout.setFocusable(false);
        drawerLayout.setClickable(false);
        drawerLayout.setEnabled(false);
    }


    public void a(List<com.coloring.page.Data> list) {
        this.horizontalAdapter = new HorizontalAdapter(list, getApplication());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        this.horizontal_recycler_view.setLayoutManager(linearLayoutManager);
        this.horizontal_recycler_view.setAdapter(this.horizontalAdapter);
        linearLayoutManager.setStackFromEnd(true);
    }


    public void b() {
        this.horizontal_recycler_view = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        this.horizontal_recycler_view_frameview = (FrameLayout) findViewById(R.id.horizontal_recycler_view_frameview);
        this.leftTop = (LinearLayout) findViewById(R.id.leftTop);
        this.myDrawView = (com.coloring.page.DrawingPicture) findViewById(R.id.draw);
        this.pen = (ImageView) findViewById(R.id.pen);
        this.choose_colortype = (ImageView) findViewById(R.id.choose_colortype);
        this.back = (ImageView) findViewById(R.id.back);
        this.eraser = (ImageView) findViewById(R.id.eraser);
        this.save = (ImageView) findViewById(R.id.save);
        this.newPage = (ImageView) findViewById(R.id.newPage);
        this.mPaint = (ImageView) findViewById(R.id.mPaint);
        iv = (ImageView) findViewById(R.id.iv);
        this.pen.setOnClickListener(this);
        this.eraser.setOnClickListener(this);
        this.save.setOnClickListener(this);
        this.back.setOnClickListener(this);
        this.newPage.setOnClickListener(this);
        this.mPaint.setOnClickListener(this);
        this.choose_colortype.setOnClickListener(this);
        this.back.setOnClickListener(this);
    }


    public void b(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.GONE);
        drawerLayout.setFocusable(false);
        drawerLayout.setClickable(false);
        drawerLayout.setEnabled(false);
    }


    public void c() {
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


    public void c(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.VISIBLE);
        drawerLayout.setFocusable(true);
        drawerLayout.setClickable(true);
        drawerLayout.setEnabled(true);
    }

    public void createNewPageDialog(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.new_page_question).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setNeutralButton(R.string.no, new paintClass2(i)).setPositiveButton(R.string.yes,

                new paintClass1(i));


        builder.create().show();
    }


    public void d() {
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
        this.writePermission = false;
        ispatternClicked = false;
    }

    public void drawerImplementationForBrush() {
        this.a = (DrawerLayout) findViewById(R.id.dr_layout_brush);
        this.drawerViewBrush = findViewById(R.id.drawer_brush);
        this.a.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.b.closeDrawer(drawActivity.drawerViewColor);
                DrawActivity drawActivity2 = DrawActivity.this;
                drawActivity2.a.closeDrawer(drawActivity2.drawerViewBrush);
                boolean unused = DrawActivity.this.isdraweropenedColor = false;
                boolean unused2 = DrawActivity.this.isdraweropenedBrush = false;
                return true;
            }
        });
        View findViewById = findViewById(R.id.ivBrushSize1);
        View findViewById2 = findViewById(R.id.ivBrushSize2);
        View findViewById3 = findViewById(R.id.ivBrushSize3);
        this.a.closeDrawer(this.drawerViewBrush);
        this.isdraweropenedBrush = false;
        findViewById.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.setBrushClick(drawActivity.a, drawActivity.drawerViewBrush, 20);
            }
        });
        findViewById2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.setBrushClick(drawActivity.a, drawActivity.drawerViewBrush, 30);
            }
        });
        findViewById3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.setBrushClick(drawActivity.a, drawActivity.drawerViewBrush, 40);
            }
        });
        this.a.addDrawerListener(new ActionBarDrawerToggle(this, this.a, R.drawable.back_1, R.string.app_name, R.string.black_bat) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                DrawActivity.this.mediaPlayer.playSound(R.raw.drawer_close);
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.a(drawActivity.a);
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                DrawActivity.this.mediaPlayer.playSound(R.raw.drawer);
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.c(drawActivity.a);
            }
        });
    }

    public void drawerImplementationForColor() {
        this.b = (DrawerLayout) findViewById(R.id.dr_layout_color);
        this.drawerViewColor = findViewById(R.id.drawer_color);
        this.b.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.a.closeDrawer(drawActivity.drawerViewBrush);
                DrawActivity drawActivity2 = DrawActivity.this;
                drawActivity2.b.closeDrawer(drawActivity2.drawerViewColor);
                boolean unused = DrawActivity.this.isdraweropenedBrush = false;
                boolean unused2 = DrawActivity.this.isdraweropenedColor = false;
                return true;
            }
        });
        View findViewById = findViewById(R.id.ivColorSize1);
        View findViewById2 = findViewById(R.id.ivColorSize2);
        View findViewById3 = findViewById(R.id.ivColorSize3);
        this.b.closeDrawer(this.drawerViewColor);
        this.isdraweropenedColor = false;
        findViewById.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.setFillType(drawActivity.b, drawActivity.drawerViewColor, 0, false);
            }
        });
        findViewById2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.setFillType(drawActivity.b, drawActivity.drawerViewColor, 1, true);
            }
        });
        findViewById3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.setFillType(drawActivity.b, drawActivity.drawerViewColor, 2, true);
            }
        });
        this.b.addDrawerListener(new ActionBarDrawerToggle(this, this.b, R.drawable.back_1, R.string.app_name, R.string.black_bat) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                DrawActivity.this.mediaPlayer.playSound(R.raw.drawer_close);
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.b(drawActivity.b);
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                DrawActivity.this.mediaPlayer.playSound(R.raw.drawer);
                DrawActivity drawActivity = DrawActivity.this;
                drawActivity.enableColorDrawer(drawActivity.b);
            }
        });
    }


    public void e() {
        com.coloring.page.MyConstant.onSizeCalled = 0;
    }


    public void f() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (DrawActivity.this.myDrawView.kidBitmap == null) {
                    DrawActivity.this.mediaPlayer.playColorRandomSound();
                }
            }
        }, 1000);
    }

    public void initializeMediaPlayer() {
        this.mediaPlayer = new MyMediaPlayer(this);
        this.mediaPlayer.playSelectArtRandomSound();
        this.mediaPlayerSoundAndMusic = new com.coloring.page.MediaPlayerSoundAndMusic();
        this.mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.welcomemain);
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
            com.coloring.page.DrawingPicture drawingPicture = this.myDrawView;
            drawingPicture.kidBitmap = Bitmap.createScaledBitmap(drawingPicture.kidBitmap, i2, i3, true);
            this.myDrawView.setKidsImage();
            if (com.coloring.page.MyConstant.selectedTool == -1) {
                com.coloring.page.MyConstant.selectedTool = 1;
            }
        }
        com.coloring.page.MyConstant.SELECTED_TOOL = 0;
        menuSelectedClick(6);
        com.coloring.page.MyConstant.strokeWidth = 0;
        com.coloring.page.MyConstant.erase = false;
    }

    public void onBackPressed() {
        com.coloring.page.MyConstant.isBackFromGlow = false;
        savePageDialogOnBackPressed();
    }
    private void NextActivity2() {
        savePageDialogOnBackPressed();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:

                if(rewardedVideoAd.isAdLoaded()) {
                    facebook.showreward(new AdClosedListener() {
                        @Override
                        public void onAdClosed() {
                            closeAllDrawer();
                            com.coloring.page.MyConstant.isBackFromGlow = false;
                            // shoInter();
                            savePageDialogOnBackPressed();
                        }
                    });
                }else{

                    closeAllDrawer();
                    com.coloring.page.MyConstant.isBackFromGlow = false;
                    // shoInter();
                    savePageDialogOnBackPressed();

                }



                break;
            case R.id.choose_colortype:
                enableColorDrawer(this.b);
                if (!this.isdraweropenedColor) {
                    this.b.openDrawer(this.drawerViewColor);
                    this.isdraweropenedColor = true;
                    return;
                }
                this.b.closeDrawer(this.drawerViewColor);
                this.isdraweropenedColor = false;
                return;
            case R.id.eraser:
                menuSelectedClick(3);
                com.coloring.page.MyConstant.SELECTED_TOOL = 2;
                com.coloring.page.MyConstant.strokeWidth = com.coloring.page.MyConstant.eraseWidth;
                com.coloring.page.MyConstant.erase = true;
                break;
            case R.id.mPaint:
                menuSelectedClick(6);
                com.coloring.page.MyConstant.SELECTED_TOOL = 0;
                com.coloring.page.MyConstant.strokeWidth = 0;
                com.coloring.page.MyConstant.erase = false;
                break;
            case R.id.newPage:
                menuSelectedClick(5);
                createNewPageDialog(-1);
                setDefaultColor();
                break;
            case R.id.pen:
                com.coloring.page.MyConstant.SELECTED_TOOL = 1;
                com.coloring.page.MyConstant.erase = false;
                menuSelectedClick(1);
                c(this.a);
                if (!this.isdraweropenedBrush) {
                    this.a.openDrawer(this.drawerViewBrush);
                    this.isdraweropenedBrush = true;
                    return;
                }
                this.a.closeDrawer(this.drawerViewBrush);
                this.isdraweropenedBrush = false;
                return;
            case R.id.save:
                menuSelectedClick(4);
                savePageDialog();
                break;
            default:
                return;
        }
        closeAllDrawer();
    }


    Facebook facebook = new Facebook();
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MyApplication.setContext(this);
        facebook.fbinit(this);

    }

    public void onDestroy() {
        super.onDestroy();
        Bitmap bitmap = com.coloring.page.DrawingPicture.canvasBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            com.coloring.page.DrawingPicture.canvasBitmap = null;
        }
        Bitmap bitmap2 = this.myDrawView.kidBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
        }
    }

    public void onPause() {
        c();
        super.onPause();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
        //Ad_class.loadAd(DrawActivity.this);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 1) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                this.writePermission = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.save_alert), Toast.LENGTH_LONG).show();
                    return;
                }
                return;
            }
            this.writePermission = true;
            saveBitmap();
        }
    }


    public void onResume() {
        c();
        b(this.b);
        a(this.a);
        this.mediaPlayerSoundAndMusic.startMainMusic();
        super.onResume();
    }


    public void onStart() {
        c();
        super.onStart();
    }

    public void onStop() {
        super.onStop();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            c();
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
            }
        }).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {


//                Ad_class.showInterstitial(DrawActivity.this, new Ad_class.onLisoner() {
//
//                    public void click() {
//                    }
//                });
                DrawActivity.this.saveBitmap();

            }
        });
        builder.create().show();
    }

    public void savePageDialogOnBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.store_picture_title).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                DrawActivity.this.finishActivityNoSave();
            }
        }).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

                DrawActivity.this.saveBitmapOnBackPressed();

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
        arrayList.add(new com.coloring.page.Data(29, R.drawable.c1_red1, "Image 2", R.color.color_new));
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

    public void setDefaultColor() {
        com.coloring.page.MyConstant.DRAW_COLOR = ContextCompat.getColor(this, R.color.color_new);
        this.horizontal_recycler_view_frameview.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
        this.leftTop.setBackgroundColor(com.coloring.page.MyConstant.DRAW_COLOR);
        turnEraserToBrush();
        this.row_index = 28;
        this.horizontalAdapter.notifyDataSetChanged();
    }

    public void turnEraserToBrush() {
        menuSelectedClick(1);
        com.coloring.page.MyConstant.SELECTED_TOOL = 1;
        com.coloring.page.MyConstant.strokeWidth = com.coloring.page.MyConstant.BRUSH_SIZE;
        com.coloring.page.MyConstant.erase = false;
    }
}
