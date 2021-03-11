package parentalcontrolapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parentalcontrolapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class facultyAdapter extends RecyclerView.Adapter<facultyAdapter.myViewHolder> {

private List<model>mDataList;
private Context context;


    public facultyAdapter(java.util.List<model> mDataList, Context context) {
        this.mDataList = mDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_faculty_layout, parent, false);
        return new myViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
 model view =mDataList.get(position);
 holder.textView.setText(view.getName());
    }

    @Override
    public int getItemCount()
    {
        return mDataList.size();
    }

    public  static class  myViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.txt_faculty);
        }
    }
}
