package com.quochung.covid20.cacmuc;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quochung.covid20.ChuyenDoi;
import com.quochung.covid20.MainActivity;
import com.quochung.covid20.R;
import com.quochung.covid20.api.APIClient;
import com.quochung.covid20.api.APIInterface;
import com.quochung.covid20.docbaiviet;
import com.quochung.covid20.models.Article;
import com.quochung.covid20.models.TinTuc;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tintuc extends Fragment {

    private RecyclerView cacbaitintuc;
    private RecyclerView.LayoutManager layoutManager;
    public static final String API_KEY = "fdd4be279a254b22b191425efe19514b";
    private List<Article> articles = new ArrayList<>();
    private String TAG = MainActivity.class.getSimpleName();
    private ChuyenDoi chuyendoi;
    View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tintuc, container, false);

        RecyclerView cacbaitintuc = view.findViewById(R.id.cacbaitintuc);
        layoutManager = new LinearLayoutManager(getActivity());
        cacbaitintuc.setLayoutManager(layoutManager);
        cacbaitintuc.setItemAnimator(new DefaultItemAnimator());
        cacbaitintuc.setNestedScrollingEnabled(false);
        OverScrollDecoratorHelper.setUpOverScroll(cacbaitintuc, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        LoadJson();

        return view;


    }


    public void LoadJson() {

        APIInterface apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<TinTuc> call;
        call = apiInterface.getNews("dịch covid", "publishedAt",API_KEY);

        call.enqueue(new Callback<TinTuc>() {
            @Override
            public void onResponse(Call<TinTuc> call, Response<TinTuc> response) {
                if (response.isSuccessful() && response.body().getArticle() != null) {
                    articles = response.body().getArticle();
                    RecyclerView cacbaitintuc = view.findViewById(R.id.cacbaitintuc);
                    if (!articles.isEmpty()) {
                        chuyendoi = new ChuyenDoi(articles, getActivity());
                        cacbaitintuc.setAdapter(chuyendoi);
                        chuyendoi.notifyDataSetChanged();
                        ChuyenDoi.hoantat(view);
                        initListener();

                    } else {
                        Toast.makeText(getActivity(), "Không có kết quả, trống", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Không có kết quả, không cào được bài viết", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TinTuc> call, Throwable t) {
                Toast.makeText(getActivity(), "Không có kết quả, lỗi API", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initListener() {
        ChuyenDoi.setOnItemClickListener(new ChuyenDoi.OnItemClickListener() {
            public void onItemClick(View view, int i) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(getActivity(), docbaiviet.class);
                Article article = articles.get(i);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img", article.getUrlimage());
                intent.putExtra("date", article.getTime());
                intent.putExtra("source", article.getSource().getName());
                intent.putExtra("author", article.getAuthor());
                getActivity().startActivity(intent);

            }
        });
    }



    }

