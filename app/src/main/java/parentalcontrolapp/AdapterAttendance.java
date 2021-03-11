package parentalcontrolapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.parentalcontrolapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class  AdapterAttendance extends RecyclerView.Adapter<AdapterAttendance.myViewHolder> {

    private List<modelattendance> SDataList;
    private Context context;

    public AdapterAttendance(List<modelattendance> SDataList, Context context) {
        this.SDataList = SDataList;
        this.context = context;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_updateattendance_layout, parent, false);
        return new AdapterAttendance.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        modelattendance view =SDataList.get(position);
        holder.fid.setText("Faculty ID : "+view.getFacultyid());
        holder.sid.setText("Student ID : "+view.getStudentid());
        holder.Sname.setText("Student : "+view.getStdname());
        holder.date.setText("Date : "+view.getDate());
        holder.time.setText("Time : "+view.getTime());

        if(view.getStatus().equals("P"))
        holder.status.setChecked(true);
        else
            holder.status.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return SDataList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView  fid, sid, Sname, date, time;
        CheckBox status;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            fid = itemView.findViewById(R.id.facultyid);
            sid = itemView.findViewById(R.id.studentid);
            Sname = itemView.findViewById(R.id.studentname);
            date = itemView.findViewById(R.id.Date);
            time = itemView.findViewById(R.id.Time);
            status = itemView.findViewById(R.id.status);


        }
    }

}