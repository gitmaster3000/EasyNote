package afk.easynote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by The Abster on 11/18/2015.
 */

public class Note {
    public String text;
    public long id;
    public String title;
    public int type;
    public List<String> tags;
    //date of reminder

    public Note() {
        type = -1;
        tags = new ArrayList<String>();
        id = -1;
    }

    public Note(String body, String t, int ty)
    {
        text = body;
        title = t;
        type = ty;
        id = -1;
        tags = new ArrayList<String>();
    }

    public Note(String body, String t, int ty, ArrayList<String> temptags)
    {
        id = -1;
        text = body;
        title = t;
        type = ty;
        tags = new ArrayList<String>();
        for (String s : temptags)
        {
            tags.add(new String(s));
        }
    }

    public Note(int i, String body, String t, int ty)
    {
        id = i;
        text = body;
        title = t;
        type = ty;
        tags = new ArrayList<String>();
    }

    public Note(int i, String body, String t, int ty, ArrayList<String> temptags)
    {
        id = i;
        text = body;
        title = t;
        type = ty;
        tags = new ArrayList<String>();
        for (String s : temptags)
        {
            tags.add(new String(s));
        }
    }

}
