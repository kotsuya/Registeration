package com.example.yoo.Registeration;

import android.content.Context;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class StatisticsCourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course>  courseList;
    private Fragment parentFrag;
    private  String userID = MainActivity.userID;

    public StatisticsCourseListAdapter(Context context, List<Course> noticeList, Fragment parent) {
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
        View v = View.inflate(context, R.layout.statistics, null);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseRate = (TextView) v.findViewById(R.id.courseRate);

        if (courseList.get(position).getCourseGrade().equals("no limit") || courseList.get(position).getCourseGrade().equals("")) {
            courseGrade.setText("all grade");
        } else {
            courseGrade.setText(courseList.get(position).getCourseGrade() + "Grade");
        }

        courseTitle.setText(courseList.get(position).getCourseTitle());
        courseDivide.setText(courseList.get(position).getCourseDivid() + "Divide");

        if (courseList.get(position).getCourseGrade().equals("no limit") || courseList.get(position).getCourseGrade().equals("")) {
            courseGrade.setText("no limit");
            courseRate.setText("");
        } else {
            coursePersonnel.setText(courseList.get(position).getCourseRival() + " / " + courseList.get(position).getCoursePersonnel());
            int rate = ((int)(((double) courseList.get(position).getCourseRival() * 100 / courseList.get(position).getCoursePersonnel()) + 0.5));
            courseRate.setText("" + rate + "%");
            if (rate < 20) {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorSafe));
            } else if (rate <= 50) {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
            } else if (rate <= 100) {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorDanger));
            } else if (rate <= 100) {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorWaning));
            } else {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorRed));
            }
        }

        v.setTag(courseList.get(position).getCourseID());

        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = MainActivity.userID;
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRespose = new JSONObject(response);
                            boolean success = jsonRespose.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parentFrag.getActivity());
                                AlertDialog dialog = builder.setMessage("Add success")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                StatisticsFragment.totalCredit -= courseList.get(position).getCourseCredit();
                                StatisticsFragment.credit.setText(StatisticsFragment.totalCredit + "Credit");
                                courseList.remove(position);
                                notifyDataSetChanged();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parentFrag.getActivity());
                                AlertDialog dialog = builder.setMessage("delete failed")
                                        .setNegativeButton("Retry", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                DeleteRequest deleteRequest = new DeleteRequest(userID, courseList.get(position).getCourseID(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentFrag.getActivity());
                queue.add(deleteRequest);
            }
        });

        return v;
    }


}
