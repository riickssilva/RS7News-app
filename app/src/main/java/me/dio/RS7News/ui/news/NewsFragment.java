package me.dio.RS7News.ui.news;

import static me.dio.RS7News.databinding.FragmentNewsBinding.inflate;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import me.dio.RS7News.R;
import me.dio.RS7News.databinding.FragmentNewsBinding;
import me.dio.RS7News.ui.adapters.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));

        observeNews();
        observeStates();

        binding.srlNews.setOnRefreshListener(newsViewModel::findNews);

        return root;
    }

    private void observeNews() {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, newsViewModel::saveNews));
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void observeStates() {
        newsViewModel.getState().observe(getViewLifecycleOwner(), this::onChanged);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onChanged(@NonNull NewsViewModel.State state) {
        switch (state) {
            case DOING:
                binding.srlNews.setRefreshing(true);
                break;
            case DONE:
                binding.srlNews.setRefreshing(false);
                break;
            case ERROR:
                binding.srlNews.setRefreshing(false);
                Snackbar.make(binding.srlNews, R.string.network_error, Snackbar.LENGTH_SHORT).show();
        }
    }
}