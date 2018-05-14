package com.example.yoo.Registeration;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class RankListAdapter extends BaseAdapter {

    private Context context;
    private List<Course>  courseList;
    private Fragment parentFrag;

    public RankListAdapter(Context context, List<Course> noticeList, Fragment parent) {
        this.context = context;
        this.courseList = noticeList;
        this.parentFrag = parent;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View v = View.inflate(context, R.layout.rank, null);
        TextView rankTextView = (TextView) v.findViewById(R.id.rankTextView);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseProfesssor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);

        rankTextView.setText((position + 1));
        if (position !=0) {
            rankTextView.setBackgroundColor(parent.getResources().getColor(R.color.colorPrimary));
        }

        if (courseList.get(position).getCourseGrade().equals("no limit") || courseList.get(position).getCourseGrade().equals("")) {
            courseGrade.setText("all grade");
        } else {
            courseGrade.setText(courseList.get(position).getCourseGrade() + "Grade");
        }

        courseTitle.setText(courseList.get(position).getCourseTitle());
        courseCredit.setText(courseList.get(position).getCourseCredit());
        courseDivide.setText(courseList.get(position).getCourseDivid() + "Divide");

        if (courseList.get(position).getCourseGrade().equals("no limit") || courseList.get(position).getCourseGrade().equals("")) {
            courseGrade.setText("no limit");
        } else {
            coursePersonnel.setText("limit : " + courseList.get(position).getCoursePersonnel());
        }

        if (courseList.get(position).getCourseProfessor().equals("")) {
            courseProfesssor.setText("Personal");
        } else {
            courseProfesssor.setText(courseList.get(position).getCourseProfessor());
        }

        courseTime.setText(courseList.get(position).getCourseTime() + "");
        v.setTag(courseList.get(position).getCourseID());

        return v;
    }


}
