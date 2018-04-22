import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnlineRadioDatabase {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int numSongs = Integer.parseInt(scanner.nextLine());

        ArrayList<Song> songs = new ArrayList<>();
        String line;
        String reg = "([\\w, \\s]+);([\\w, \\s]+);(([0-9]+):([0-9]+))";
        Pattern pattern = Pattern.compile(reg);
        Matcher match;
        for(int i = 0; i<numSongs; i++)
        {
            line = scanner.nextLine();
            match = pattern.matcher(line);
            try
            {
                songs.add(Song.parseSong(match));
                System.out.println("Song added.");
            }
            catch (InvalidSongException e)
            {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Songs added: " + songs.size());
        System.out.println("Playlist length: " + songs.stream().map(x -> x.getLength()).reduce((x, y) -> x.add(y)).toString());
    }

    public static class InvalidSongException extends Exception
    {
        public InvalidSongException() {
            super("Invalid song.");
        }

        public InvalidSongException(String message) {
            super(message);
        }
    }
    public static class InvalidArtistNameException extends InvalidSongException
    {
        public InvalidArtistNameException() {
            super("Artist name should be between 3 and 20 symbols.");
        }
    }
    public static class InvalidSongNameException extends InvalidSongException
    {
        public InvalidSongNameException() {
            super("Song name should be between 3 and 30 symbols.");
        }
    }
    public static class InvalidSongLengthException extends InvalidSongException
    {
        public InvalidSongLengthException() {
            super("Invalid song length.");
        }

        public InvalidSongLengthException(String message) {
            super(message);
        }
    }
    public static class InvalidSongMinutesException extends InvalidSongLengthException
    {
        public InvalidSongMinutesException() {
            super("Song minutes should be between 0 and 14.");
        }
    }
    public static class InvalidSongSecondsException extends InvalidSongLengthException
    {
        public InvalidSongSecondsException() {
            super("Song seconds should be between 0 and 59.");
        }
    }

    public static class Time
    {
        int hours;
        int minutes;
        int seconds;

        public Time() {
            hours = 0;
            minutes = 0;
            seconds = 0;
        }

        public Time(int hours, int minutes, int seconds) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }

        public Time add(Time time)
        {
            this.seconds += time.seconds;
            if(this.seconds > 59)
            {
                this.seconds -= 59;
                this.minutes++;
            }
            this.minutes += time.minutes;
            if(this.minutes >= 59)
            {
                this.minutes -= 59;
                this.hours++;
            }
            this.hours += time.hours;
            return this;
        }

        public String toString()
        {
            return String.valueOf(hours) + "h " + String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s";
        }

        public static Time parseTime(String source)
        {
            Time t = new Time();
            String args[] = source.split(":");
            t.seconds = Integer.parseInt(args[1]);
            t.minutes = Integer.parseInt(args[0]);
            t.hours = 0;
            return t;
        }
    }

    public static class Song
    {
        private String artist;
        private String song;
        private Time length;

        public Song(String artist, String song, Time length) {
            this.artist = artist;
            this.song = song;
            this.length = length;
        }

        public String getArtist() {
            return artist;
        }

        public String getSong() {
            return song;
        }

        public Time getLength() {
            return length;
        }

        public static Song parseSong(Matcher match) throws InvalidSongException
        {
            if(match.group(1).length() < 3 || match.group(1).length() > 20)
                throw new InvalidArtistNameException();
            else if(match.group(2).length() < 3 || match.group(2).length() > 30)
                throw new InvalidSongNameException();
            else if(Integer.parseInt(match.group(4)) > 14)
                throw new InvalidSongMinutesException();
            else if(Integer.parseInt(match.group(5)) > 59)
                throw new InvalidSongSecondsException();
            else
            {
                Time l = Time.parseTime(match.group(3));
                return new Song(match.group(1), match.group(2), l);
            }
        }
    }
}
