package com.example.kabubufix.Kuis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kabubufix.Kuis.QuizContract.*;

import androidx.annotation.ContentView;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyKabubuQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTION_TABLE = " CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NUMBER + " INTEGER " +
                " ) ";

        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillQuestionTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);

    }


    private void addQuestion(Question question){

        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER_NUMBER, question.getAnswerNumber());

        db.insert(QuestionTable.TABLE_NAME, null, cv);
    }



    // retrieve semua pertanyaan dr database
   public ArrayList<Question> getAllQuestions() {

        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME + " ORDER BY RANDOM() " +
                " LIMIT 10 " , null);

        if (c.moveToFirst()){
            do{
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNumber(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NUMBER)));
                questionList.add(question);

            }while (c.moveToNext());
        }

        c.close();
        return questionList;
   }



    // isi dari tabel pertanyaan
    private void fillQuestionTable() {

        // Level Easy ------------------------------------------------------------------------------------------------------
        Question q1 = new Question("Budayawan Indonesia yang menulis buku 'Secangkir Kopi Jon Pakir' adalah ? ",
                "Emha Ainun Nadjib",
                "Sudjiwo Tedjo",
                "Nurcholis Madjid",
                "W.S Rendra",
                1);
        addQuestion(q1);

        Question q2 = new Question("'Pemikiran Karl Marx' adalah judul buku karya ?",
                "Abdurrahman Wahid",
                "Franz Magnis Suseno",
                "Ir. Soekarno",
                "Sutardji Calzoum Bachri",
                2);
        addQuestion(q2);

        Question q3 = new Question("Berikut ini manakah judul dari buku karya Sudjiwo Tedjo ? ",
                "Slilit Sang Kiai",
                "O Amuk Bapak",
                "Rahvayana Aku Lala Padamu",
                "Katulistiwa",
                3);
        addQuestion(q3);

        Question q4 = new Question("Manakah judul puisi karya W.S Rendra ?",
                "Kubakar Cintaku",
                "Takon Wong",
                "Aku Berkaca",
                "Doa Seorang serdadu Sebelum perang",
                4);
        addQuestion(q4);

        Question q5 = new Question("'Dhandanggula Sidoasih' merupakan puisi karya?",
                "W.S Rendra",
                "Emha Ainun Nadjib",
                "Sutardji C Bachri",
                "Sudjiwo Tedjo",
                4);
        addQuestion(q5);

        Question q6 = new Question("'Aku Lala Padamu' merupakan buku karya?",
                "Sudjiwo Tedjo",
                "Abdul Hadi W.M",
                "Mustofa Bisri",
                "Sutardji C Bachri",
                1);
        addQuestion(q6);

        Question q7 = new Question("'Ana Bunga' merupakan puisi karya?",
                "Emha Ainun Nadjib",
                "Sutardji C Bachri",
                "Abdurrahman Wahid",
                "Mustofa Bisri",
                2);
        addQuestion(q7);

        Question q8 = new Question("Dimanakah kota tempat kelahiran tokoh budayawan Ahmad Mustofa Bisri?",
                "Malang, Jawa Timur",
                "Jombang, Jawa Timur",
                "Rembang, Jawa Tengah",
                "Semarang, Jawa Tengah",
                3);
        addQuestion(q8);

        Question q9 = new Question("Berikut ini yang bukan merupakan judul puisi dari Sutardji Calzoum Bachri",
                "Ana Bunga",
                "Gajah Dan Semut",
                "Syair Dunia Maya",
                "Amuk",
                3);
        addQuestion(q9);

        Question q10 = new Question("'Saleh Ritual, Saleh Sosial' merupakan buku karya?",
                "A. Mustofa Bisri",
                "Abdurrahman Wahid",
                "Abdul Hadi WM",
                "Sudjiwo Tedjo",
                1);
        addQuestion(q10);

        // Level Medium ------------------------------------------------------------------------------------------------------------

        Question q11 = new Question("Tokoh budayawan Indonesia yang lahir pada tanggal 27 Mei 1953 adalah?",
                "Abdurrahman Wahid",
                "Sutardji Calzoum Bachri",
                "Nurcholis Majid",
                "Ridwan Saidi",
                1);
        addQuestion(q11);

        Question q12 = new Question("'Profil Orang Betawi' merupakan buku karya ?",
                "Umar Kayam",
                "Abdul Hadi WM",
                "Nugroho Notosusanto",
                "Ridwan Saidi",
                4);
        addQuestion(q12);

        Question q13 = new Question("Tanggal berapakah budayawan Umar Kayam lahir ?",
                "30 April 1923",
                "30 April 1932",
                "30 Mei 1932",
                "30 Mei 1923",
                2);
        addQuestion(q13);

        Question q14 = new Question("Jalan Meikung merupakan buku karya?",
                "A Mustofa Bisri",
                "Taufik Ismail",
                "Abdul Hadi WM",
                "Umar Kayam",
                4);
        addQuestion(q14);

        Question q15 = new Question("Dimanakah kota tempat kelahiran Taufik Ismail?",
                "Solo",
                "Bukittinggi",
                "Lampung",
                "Pekalongan",
                2);
        addQuestion(q15);

        Question q16 = new Question("Para Priyayi merupakan buku karya?",
                "Umar Kayam",
                "Adsul Hadi WM",
                "Sujiwo Tejo",
                "Gusdur",
                1);
        addQuestion(q16);

        Question q17 = new Question("Berikut ini manakah yang merupakan judul dari buku karya Taufik Ismail",
                "Manusia Ulang-alik",
                "Gajah dan Semut",
                "Kembalikan Indonesia Padaku",
                "Secangkir Kopi Jon Pakir",
                3);
        addQuestion(q17);

        Question q18 = new Question("Mencari Sebuah Mesjid merupakan puisi karya?",
                "Franz Magnis Suseno",
                "Sujiwo Tejo",
                "Gus Mus",
                "Taufik Ismail",
                4);
        addQuestion(q18);

        Question q19 = new Question("Budayawan Indonesia yang juga pernah menjabat sebagai presiden ke 4 adalah ?",
                "Abdurrahman Wahid",
                "Ahmad Mustofa Bisri",
                "Abdul Hadi WM",
                "Nurcholis Majid",
                1);
        addQuestion(q19);

        Question q20 = new Question("Budayawan betawi yang pernah menjadi anggota DPR pada periode 1977-1987 adalah?",
                "Benyamin Sueb",
                "Ridwan Saidi",
                "Rano Karno",
                "Budiman Sujatmiko",
                2);
        addQuestion(q20);

        Question q21 = new Question("'Tuhan, kita begitu dekat' merupakan judul buku karya?",
                "W S Rendra",
                "Taufik Ismail",
                "Abdul Hadi WM",
                "Emha Ainun Najib",
                3);
        addQuestion(q21);

        Question q22 = new Question("'Lagu Dalam Hujan' merupakan judul puisi karya?",
                "W S Rendra",
                "Taufik Ismail",
                "Abdul Hadi WM",
                "Emha Ainun Najib",
                3);
        addQuestion(q22);


    }

}
