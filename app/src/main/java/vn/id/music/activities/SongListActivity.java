package vn.id.music.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.id.music.R;
import vn.id.music.adapters.CategoryAdapter;
import vn.id.music.adapters.SongAdapter;
import vn.id.music.models.Category;
import vn.id.music.models.Song;

public class SongListActivity extends AppCompatActivity {

    private Spinner spnCategory;
    private RecyclerView rvSongList;
    public static List<Song> songList, songListFixed;
    public List<Category> categoryList;
    private SongAdapter songAdapter;
    private CategoryAdapter categoryAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.song_list_title);

        initViews();

        initData();

        songAdapter = new SongAdapter(this, i -> {
            Intent intent = new Intent(SongListActivity.this, MusicPlayerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvSongList.setLayoutManager(linearLayoutManager);
        rvSongList.setHasFixedSize(true);
        rvSongList.setItemViewCacheSize(15);
        rvSongList.setDrawingCacheEnabled(true);
        rvSongList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvSongList.addItemDecoration(itemDecoration);

        songAdapter.setData(songList);

        rvSongList.setAdapter(songAdapter);


        categoryAdapter = new CategoryAdapter(this, categoryList);

        spnCategory.setAdapter(categoryAdapter);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                songList.clear();
                if (i == 0) {
                    songList.addAll(songListFixed);
                } else {
                    String categoryName = categoryList.get(i).getName().toLowerCase();
                    songListFixed.forEach(song -> {
                        if (song.getCategory().toLowerCase().equals(categoryName))
                            songList.add(song);
                    });
                }
                songAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initData() {
        categoryList = new ArrayList<>();
        categoryList.add(new Category("Tất cả thể loại"));
        categoryList.add(new Category("Nhạc Âu Mỹ"));
        categoryList.add(new Category("Nhạc trẻ"));
        categoryList.add(new Category("Nhạc trữ tình"));

        songList = new ArrayList<>();
        songList.add(new Song("Hãy Trao Cho Anh", "Sơn Tùng M-TP, Snoop Dogg", R.raw.hay_trao_cho_anh, R.drawable.hay_trao_cho_anh, "Nhạc trẻ"));
        songList.add(new Song("Em Của Ngày Hôm Qua", "Sơn Tùng M-TP", R.raw.em_cua_ngay_hom_qua, R.drawable.em_cua_ngay_hom_qua, "Nhạc trẻ"));
        songList.add(new Song("Đèn Khuya", "Hồ Văn Cường", R.raw.den_khuya, R.drawable.den_khuya, "Nhạc trữ tình"));
        songList.add(new Song("Từng Yêu", "Phan Duy Anh", R.raw.tung_yeu, R.drawable.tung_yeu, "Nhạc trẻ"));
        songList.add(new Song("Attention", "Charlie Puth", R.raw.attention, R.drawable.attention, "Nhạc Âu Mỹ"));
        songList.add(new Song("Chạm Đáy Nỗi Đau", "Erik", R.raw.cham_day_noi_dau, R.drawable.cham_day_noi_dau, "Nhạc trẻ"));
        songList.add(new Song("Một Bước Yêu Vạn Dặm Đau", "Mr.Siro", R.raw.mot_buoc_yeu_van_dam_dau, R.drawable.mot_buoc_yeu_van_dam_dau, "Nhạc trẻ"));
        songList.add(new Song("Sao Em Vô Tình", "Jack, Liam", R.raw.sao_em_vo_tinh, R.drawable.sao_em_vo_tinh, "Nhạc trẻ"));
        songList.add(new Song("Thay Tôi Yêu Cô Ấy", "Thanh Hưng", R.raw.thay_toi_yeu_co_ay, R.drawable.thay_toi_yeu_co_ay, "Nhạc trẻ"));
        songList.add(new Song("Nơi Này Có Anh", "Sơn Tùng M-TP", R.raw.noi_nay_co_anh, R.drawable.noi_nay_co_anh, "Nhạc trẻ"));
        songList.add(new Song("Hello", "Adele", R.raw.hello, R.drawable.hello, "Nhạc Âu Mỹ"));
        songList.add(new Song("Bước Qua Đời Nhau", "Lê Bảo Bình", R.raw.buoc_qua_doi_nhau, R.drawable.buoc_qua_doi_nhau, "Nhạc trẻ"));
        songList.add(new Song("Phía Sau Một Cô Gái", "SOOBIN", R.raw.phia_sau_mot_co_gai, R.drawable.phia_sau_mot_co_gai, "Nhạc trẻ"));
        songList.add(new Song("See You Again", "Wiz Khalifa, Charlie Puth", R.raw.see_you_again, R.drawable.see_you_again, "Nhạc Âu Mỹ"));
        songList.add(new Song("Sông Quê", "Hồng Thắm ,Hoàng Phong", R.raw.song_que, R.drawable.song_que, "Nhạc trữ tình"));
        songList.add(new Song("Lạc Trôi", "Sơn Tùng M-TP", R.raw.lac_troi, R.drawable.lac_troi, "Nhạc trẻ"));
        songList.add(new Song("Sống Xa Anh Chẳng Dễ Dàng", "Bảo Anh", R.raw.song_xa_anh_chang_de_dang, R.drawable.song_xa_anh_chang_de_dang, "Nhạc trẻ"));
        songList.add(new Song("Sao Trời Làm Gió", "Nal", R.raw.sao_troi_lam_gio, R.drawable.sao_troi_lam_gio, "Nhạc trữ tình"));

        songListFixed = new ArrayList<>(songList);

    }

    private void initViews() {
        spnCategory = findViewById(R.id.spn_category);
        rvSongList = findViewById(R.id.rv_song_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                songAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                songAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}