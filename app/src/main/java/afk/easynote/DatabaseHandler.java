package afk.easynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by The Abster on 11/18/2015.
 */


public class DatabaseHandler extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHandler";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Notes";

    // Table Names
    /*
private static final String TABLE_NOTES = "notes";
private static final String TABLE_TAG = "tags";
private static final String TABLE_TODO_TAG = "notes_tags";

create table notes
	id int pk
	title text
	note_text text
    type int

create table tags
	tag_name text

create table note_tags
	id int pk
	note_id int fk
	tag_name text fk

*/

        //SHOULD I CLOSE DB CONNECTION EVER?


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating table notes
        db.execSQL("create table notes (id integer primary key, title text, note_text text, type integer)");
        db.execSQL("create table tags (tag_name text primary key)");
        db.execSQL("create table note_tags (id integer primary key, note_id integer, tag_name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //do something
    }

    public long insertNoteOrReminder (Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", note.title);
        cv.put("note_text", note.text);  //Column_name, value
        cv.put("type", note.type);

        long note_id = db.insert("notes", null, cv);

        for (String tag : note.tags)
        {
            insertNoteTagPair(note_id, tag);
        }

        return note_id;
    }

    public void clearNotesTable ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("notes", null, null);
    }

    public void clearTagsTable ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tags", null, null);
    }

    public void clearNote_TagsTable ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("note_tags", null, null);
    }

    public void clearAllTables ()
    {
        clearNotesTable();
        clearTagsTable();
        clearNote_TagsTable();
    }

    public long updateNoteOrReminder (Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", note.title);
        cv.put("note_text", note.text);
        cv.put("type", note.type);

        return db.update("notes", cv, "id = ?", new String[] {String.valueOf(note.id)});

    }

    public Note getNoteOrReminder (long noteid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.e(LOG, "getting a note with particular id");
        Cursor cur = db.rawQuery("select * from notes where id = " + noteid, null);
        Note n = new Note ();
        if (cur != null)
        {
            cur.moveToFirst();
            n.id = cur.getInt(cur.getColumnIndex("id"));
            n.text = cur.getString(cur.getColumnIndex("note_text"));
            n.title = cur.getString(cur.getColumnIndex("title"));
            n.type = cur.getInt(cur.getColumnIndex("type"));
            cur = db.rawQuery("select tag_name from note_tags where note_id = " + noteid, null);
            if (cur.moveToFirst())
            {
                do
                {
                    n.tags.add(new String(cur.getString(0)));
                } while (cur.moveToNext());
            }
        }

        return n;
    }

    public List<Note> getAllNotes ()
    {
        List<Note> mNotes = new ArrayList<Note>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("select * from notes where type = 0", null);

        if (cur.moveToFirst())
        {
            do
            {
                Note n = new Note ();
                n.id = cur.getInt(cur.getColumnIndex("id"));
                n.text = cur.getString(cur.getColumnIndex("note_text"));
                n.title = cur.getString(cur.getColumnIndex("title"));
                n.type = cur.getInt(cur.getColumnIndex("type"));
                Cursor cur2 = db.rawQuery("select tag_name from note_tags where note_id = " + n.id, null);
                if (cur2.moveToFirst())
                {
                    do
                    {
                        n.tags.add(new String(cur2.getString(0)));
                    } while (cur2.moveToNext());
                }
                mNotes.add(n);
            } while (cur.moveToNext());
        }

        return mNotes;
    }


    public List<Note> getAllReminders ()
    {
        List<Note> mNotes = new ArrayList<Note>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("select * from notes where type = 1", null);

        if (cur.moveToFirst())
        {
            do
            {
                Note n = new Note ();
                n.id = cur.getInt(cur.getColumnIndex("id"));
                n.text = cur.getString(cur.getColumnIndex("note_text"));
                n.title = cur.getString(cur.getColumnIndex("title"));
                n.type = cur.getInt(cur.getColumnIndex("type"));
                Cursor cur2 = db.rawQuery("select tag_name from note_tags where note_id = " + n.id, null);
                if (cur2.moveToFirst())
                {
                    do
                    {
                        n.tags.add(new String(cur2.getString(0)));
                    } while (cur2.moveToNext());
                }
                mNotes.add(n);
            } while (cur.moveToNext());
        }

        return mNotes;
    }


    public List<Note> getAllNotesByTag (String tag)
    {//only returns notes, NOT reminders
        List<Note> mNotes = new ArrayList<Note>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("select id, title, note_text,type from note_tags join notes on note_id = id where type = 0 and tag_name = " + tag, null);

        if (cur.moveToFirst())
        {
            do
            {
                Note n = new Note ();
                n.id = cur.getInt(0);
                n.text = cur.getString(2);
                n.title = cur.getString(1);
                n.type = cur.getInt(3);
                Cursor cur2 = db.rawQuery("select tag_name from note_tags where note_id = " + n.id, null);
                if (cur2.moveToFirst())
                {
                    do
                    {
                        n.tags.add(new String(cur2.getString(0)));
                    } while (cur2.moveToNext());
                }
                mNotes.add(n);
            } while (cur.moveToNext());
        }

        return mNotes;
    }


    public List<Note> getAllRemindersByTag (String tag)
    {//only returns notes, NOT reminders
        List<Note> mNotes = new ArrayList<Note>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("select id, title, note_text,type from note_tags join notes on note_id = id where type = 1 and tag_name = " + tag, null);

        if (cur.moveToFirst())
        {
            do
            {
                Note n = new Note ();
                n.id = cur.getInt(0);
                n.text = cur.getString(2);
                n.title = cur.getString(1);
                n.type = cur.getInt(3);
                Cursor cur2 = db.rawQuery("select tag_name from note_tags where note_id = " + n.id, null);
                if (cur2.moveToFirst())
                {
                    do
                    {
                        n.tags.add(new String(cur2.getString(0)));
                    } while (cur2.moveToNext());
                }
                mNotes.add(n);
            } while (cur.moveToNext());
        }

        return mNotes;
    }

    public List<String> getTagsOfANoteOrReminder (long noteid)
    {
        List<String> mTags = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("select tag_name from note_tags where note_id = " + noteid, null);

        if (cur.moveToFirst())
        {
            do
            {
                mTags.add(cur.getString(0));
            } while (cur.moveToNext());
        }

        return mTags;
    }

    public long addTag (String tag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tag_name", tag);

        long tagid = db.insert("tags", null, cv);

        return tagid;
    }

    public List<String> getAllTags ()
    {
        List<String> mTags = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select tag_name from tags", null);

        if (cur.moveToFirst())
        {
            do {
                mTags.add(cur.getString(0));
            } while (cur.moveToNext());
        }

        return mTags;
    }


    public long insertNoteTagPair (long note_id, String tag)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("note_id", note_id);
        cv.put("tag_name", tag);

        long id = db.insert("note_tags", null, cv);

        return id;
    }

    public void deleteNoteOrReminder (long noteid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("note_tags", "note_id = ?", new String[]{String.valueOf(noteid)});
        db.delete("notes","id = ?", new String[] {String.valueOf(noteid)});
    }

    public void deleteTag (String tag)
    { //should functionality to delete all notes with this tag be provided?
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("note_tags", "tag_name = ?", new String[] {tag});
        db.delete("tags", "tag_name = ?", new String[] {tag});
    }


    public void removeTagOfANote (long noteid, String tag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("note_tags", "note_id = ? and tag_name = ?", new String[] {String.valueOf(noteid), tag});

    }


}
