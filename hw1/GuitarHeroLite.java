import synthesizer.GuitarString;

/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
public class GuitarHeroLite {
    private static final double CONCERT_A = 440.0;
    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static GuitarString[] strings = new GuitarString[37];

    public static void main(String[] args) {
        for (char c: KEYBOARD.toCharArray()) {
            GuitarString t = new GuitarString(getCONCERT(KEYBOARD.indexOf(c)));
            strings[KEYBOARD.indexOf(c)] = t;
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (KEYBOARD.indexOf(key) == -1) {
                    continue;
                }
                strings[KEYBOARD.indexOf(key)].pluck();
            }

        /* compute the superposition of samples */
            double sample = 0.0;
            for (GuitarString string : strings) {
                sample += string.sample();
            }

        /* play the sample on standard audio */
            StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */
            for (GuitarString string : strings) {
                string.tic();
            }
        }
    }

    private static double getCONCERT(int index) {
        return CONCERT_A * Math.pow(2, (index - 24) / 12.0);
    }
}

