package vn.id.music.activities;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.id.music.R;
import vn.id.music.models.Song;
import vn.id.music.utils.TimeUtils;

public class MusicPlayerActivity extends AppCompatActivity {

    private TextView tvSongName, tvArtist, tvTotalTime, tvCurrentTime;
    private CircleImageView ivDisk;
    private ImageButton btnNext, btnPlay, btnPrev, btnRandom, btnRepeat;
    private SeekBar sbSong;
    private Song song;
    private int index;
    private MediaPlayer player;
    private ObjectAnimator animator;
    private Handler handler;
    private Runnable runnable;
    private boolean isRandom, isRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        initViews();
        ImageButton button = findViewById(R.id.btn_random);

        animator = ObjectAnimator.ofFloat(ivDisk, "rotation", 0, 360);
        animator.setDuration(25 * 1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (player != null) {
                    String currentTime = TimeUtils.formatTime(player.getCurrentPosition());
                    tvCurrentTime.setText(currentTime);
                    sbSong.setProgress(player.getCurrentPosition());
                    handler.postDelayed(this, 300);
                }
            }
        };

        Bundle bundle = getIntent().getExtras();
        index = bundle.getInt("index");
        song = SongListActivity.songList.get(index);

        btnPlay.setOnClickListener(view -> {
            if (player.isPlaying()) {
                player.pause();
                animator.pause();
                btnPlay.setImageResource(R.drawable.ic_play);
                handler.removeCallbacks(runnable);
            } else {
                player.start();
                animator.resume();
                btnPlay.setImageResource(R.drawable.ic_pause);
                handler.post(runnable);
            }
        });

        btnNext.setOnClickListener(view -> {
            index++;
            if (index == SongListActivity.songList.size()) {
                index = 0;
            }
            releaseMediaPlayer();
            playMusic();
        });

        btnPrev.setOnClickListener(view -> {
            index--;
            if (index < 0) {
                index = SongListActivity.songList.size() - 1;
            }
            releaseMediaPlayer();
            playMusic();
        });

        btnRandom.setOnClickListener(view -> {
            isRandom = !isRandom;
            btnRandom.setImageResource(isRandom ? R.drawable.ic_random_check : R.drawable.ic_random);
        });

        btnRepeat.setOnClickListener(view -> {
            isRepeat = !isRepeat;
            btnRepeat.setImageResource(isRepeat ? R.drawable.ic_repeat_check : R.drawable.ic_repeat);
        });

        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
                handler.post(runnable);
            }
        });

    }

    private void playMusic() {
        song = SongListActivity.songList.get(index);
        player = MediaPlayer.create(this, song.getSongResourceId());
        player.setOnCompletionListener(mediaPlayer -> {
            if (isRepeat) {

            } else if (isRandom) {
                Random random = new Random();
                int newIndex;
                do {
                    newIndex = random.nextInt(SongListActivity.songList.size());
                } while (newIndex == index);
                index = newIndex;
            } else {
                index++;
                if (index == SongListActivity.songList.size()) {
                    index = 0;
                }
            }
            releaseMediaPlayer();
            playMusic();
        });
        player.start();
        animator.start();
        btnPlay.setImageResource(R.drawable.ic_pause);
        ivDisk.setImageResource(song.getImage());
        tvSongName.setText(song.getName());
        tvArtist.setText(song.getArtist());
        tvTotalTime.setText(TimeUtils.formatTime(player.getDuration()));
        tvCurrentTime.setText(TimeUtils.formatTime(player.getCurrentPosition()));
        sbSong.setMax(player.getDuration());
        handler.post(runnable);
    }

    private void initViews() {
        tvSongName = findViewById(R.id.tv_song_name_music_player);
        tvArtist = findViewById(R.id.tv_artist_music_player);
        tvCurrentTime = findViewById(R.id.tv_current_time);
        tvTotalTime = findViewById(R.id.tv_total_time);
        sbSong = findViewById(R.id.sb_song);
        btnPlay = findViewById(R.id.btn_play);
        btnNext = findViewById(R.id.btn_next);
        btnPrev = findViewById(R.id.btn_prev);
        btnRandom = findViewById(R.id.btn_random);
        btnRepeat = findViewById(R.id.btn_repeat);
        ivDisk = findViewById(R.id.iv_disk_music_player);
    }

    private void releaseMediaPlayer() {
        if (player != null) {
            handler.removeCallbacks(runnable);
            player.stop();
            player.reset();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (player != null) {
            releaseMediaPlayer();
        }
        playMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!player.isPlaying()) {
            player.start();
            handler.post(runnable);
            animator.resume();
            btnPlay.setImageResource(R.drawable.ic_pause);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        player.pause();
        animator.pause();
        btnPlay.setImageResource(R.drawable.ic_play);
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}