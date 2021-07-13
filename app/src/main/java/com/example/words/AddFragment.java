package com.example.words;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddFragment extends Fragment {
    private Button btn_submit;
    private EditText et_eg,et_ch;
    WordViewModel wordViewModel;
    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_submit = requireActivity().findViewById(R.id.btn_submit);
        et_eg = requireActivity().findViewById(R.id.et_eg);
        et_ch = requireActivity().findViewById(R.id.et_ch);
        btn_submit.setEnabled(false);
        et_eg.requestFocus();
        wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_eg,0);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String english = et_eg.getText().toString().trim();
                String chinese = et_ch.getText().toString().trim();
                btn_submit.setEnabled(!english.isEmpty() && !chinese.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        et_eg.addTextChangedListener(textWatcher);
        et_ch.addTextChangedListener(textWatcher);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String english = et_eg.getText().toString().trim();
                String chinese = et_ch.getText().toString().trim();
                Word word = new Word(english, chinese);
                wordViewModel.insertWords(word);
                NavController controller = Navigation.findNavController(v);
                controller.navigateUp();
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
    }


}