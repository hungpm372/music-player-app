package vn.id.music.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.id.music.R;
import vn.id.music.activities.SongListActivity;
import vn.id.music.interfaces.OnItemClickListener;
import vn.id.music.models.Song;
import vn.id.music.utils.TimeUtils;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> implements Filterable {

    private final Context context;
    private List<Song> songList;
    private List<Song> songListOld;
    private final OnItemClickListener onItemClickListener;

    public SongAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<Song> songList) {
        this.songList = songList;
        this.songListOld = songList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song item = songList.get(position);
        if (item == null) return;

        holder.tvSongName.setText(item.getName());
        holder.tvArtist.setText(item.getArtist());
        holder.tvDuration.setText(TimeUtils.formatTime(item.getDuration(context)));
        holder.tvThumbnail.setImageResource(item.getImage());
        holder.llSongItem.setOnClickListener(view -> onItemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        if (songList != null) return songList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String keyword = (String) charSequence;
                if (keyword.isEmpty()) {
                    SongListActivity.songList = songListOld;
                } else {
                    List<Song> list = new ArrayList<>();
                    songListOld.forEach(song -> {
                        if (song.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            list.add(song);
                        }
                    });
                    SongListActivity.songList = list;
                }

                FilterResults results = new FilterResults();
                results.values = SongListActivity.songList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                songList = (List<Song>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llSongItem;
        TextView tvSongName, tvArtist, tvDuration;
        ImageView tvThumbnail;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            llSongItem = itemView.findViewById(R.id.ll_song_item);
            tvSongName = itemView.findViewById(R.id.tv_song_name);
            tvArtist = itemView.findViewById(R.id.tv_artist);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvThumbnail = itemView.findViewById(R.id.iv_thumbnail);
        }
    }

}
