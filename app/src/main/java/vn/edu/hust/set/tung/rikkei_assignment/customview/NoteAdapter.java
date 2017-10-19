package vn.edu.hust.set.tung.rikkei_assignment.customview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.edu.hust.set.tung.rikkei_assignment.R;
import vn.edu.hust.set.tung.rikkei_assignment.model.Note;

/**
 * Created by tungt on 10/17/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.UserViewHolder> {

    List<Note> listNote;

    public NoteAdapter(List<Note> listNote) {
        this.listNote = listNote;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        Note note = listNote.get(position);
        holder.tvNoteName.setText(note.getName());
        holder.tvContent.setText(note.getContent());
        holder.tvClock.setText(note.getTime());
        if (note.isAlarm()) {
            holder.ivClock.setVisibility(View.VISIBLE);
        } else {
            holder.ivClock.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return null != listNote ? listNote.size() : 0;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteName;
        ImageView ivClock;
        TextView tvContent;
        TextView tvClock;
        public UserViewHolder(View itemView) {
            super(itemView);
            tvNoteName = itemView.findViewById(R.id.tvNoteName);
            ivClock = itemView.findViewById(R.id.ivClock);
            tvClock = itemView.findViewById(R.id.tvClock);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }

    public List<Note> getListNote() {
        return listNote;
    }

    public void setListNote(List<Note> listNote) {
        this.listNote = listNote;
    }
}
