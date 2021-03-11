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

public class studentcheckboxAdapter extends RecyclerView.Adapter<studentcheckboxAdapter.myViewHolder> {

    private List<modelStudent> SDataList;
    private Context context;


    public studentcheckboxAdapter(List<modelStudent> SDataList, Context context) {
        this.SDataList = SDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_attendance_layout, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        modelStudent view =SDataList.get(position);
        holder.textView.setText(view.getStudentName());
        holder.textViewid.setText(view.getStudent_id());

    }

    @Override
    public int getItemCount() {
        return SDataList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView textView, textViewid;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.txt_student);
            textViewid = itemView.findViewById(R.id.TextViewid);
        }
    }
}
