package tk.mihou.amatsuki.api.enums;

public enum Genre {

    /*
     * Definitions were taken from the Official NovelUpdates Genre Definition
     * with the support of Moonpearl's thread of Genre Explanations (Unofficial) for Isekai, Fanfiction and LitRPG
     * as they don't have any official description.
     *
     * Links:
     * https://forum.scribblehub.com/threads/genre-explanations-unofficial.3634/
     * https://www.novelupdates.com/genre-explanation/
     *
     */

    ACTION(9, "A work typically depicting fighting, violence, chaos, and fast paced motion."),
    ADULT(902, "Contains mature content that is suitable only for adults. Titles in this category may include prolonged scenes of intense violence and/or graphic sexual content and nudity."),
    ADVENTURE(8, "Exploring new places, environments or situations. This is often associated with people on long journeys to places far away encountering amazing things."),
    BOYS_LOVE(891,"Often synonymous with Yaoi, this can be thought of as somewhat less extreme and only hints at a relationship between two males. [Boy's Love], so to speak"),
    COMEDY(7, "A dramatic work that is light and often humorous or satirical in tone and that usually contains a happy resolution of the thematic conflict."),
    DRAMA(903,"A work meant to bring on an emotional response, such as instilling sadness or tension.\n\nNovels that often show life or characters through conflict and emotions. In general, the different parts of the story tend to form a whole that is greater than the sum of the parts. In other words, the story has a message that is bigger than just the story line itself"),
    ECCHI(904, "Possibly the line between hentai and non-hentai, ecchi usually refers to fanservice put in to attract a certain group of fans."),
    FANFICTION(38, "A fan work that makes direct use of someone else’s characters or setting. Stories that are inspired by or make use of a story that’s diffused into a cultural phenomenon generally aren’t considered fanfiction. (e.g. A story with Dracula isn’t fanfiction of Bram Stoker’s novel unless it deliberately uses Bram Stroker’s characters and/or exact setting. A new reimagining of the Little Mermaid isn’t fanfiction. Making use of the King Arthur legend wouldn’t be fanfiction.)"),
    FANTASY(19,"Anything that involves, but not limited to, magic, dream world, and fairy tales."),
    GENDERBENDER(905,"Girls dressing up as guys, guys dressing up as girls. Guys turning into girls, girls turning into guys."),
    GIRLS_LOVE(892, "Often synonymous with Yuri, this can be thought of as somewhat less extreme and only hints at a relationship between two females. [Girl's Love], so to speak."),
    HAREM(1015,"A series involving one male character and many female characters (usually attracted to the male character). A Reverse Harem is when the genders are reversed."),
    HISTORICAL(21, "Novels whose setting is in the past. Frequently these follow historical tales, sagas or facts.\n\nWorld with kingdom setting that are not from actual history saga, tales, are not included."),
    HORROR(22, "Novels whose focus is to scare the audience."),
    ISEKAI(37, "Novels in which the main character is someone from Earth who is transported into another world, becomes stuck there, and must adapt to their new setting. Very traditional isekai uses a standard fantasy setting."),
    JOSEI(906, "Literally [Woman]. Targets women 18-30. Female equivalent to seinen. Unlike shoujo the romance is more realistic and less idealized. The storytelling is more explicit and mature."),
    LITRPG(1180, "Novels set in a game or a game-like world where games and game-like challenges are encountered frequently and are essential to the story, and stats and other RPG elements are present and depicted for the reader as part of the reading experience. Stats must be a significant part of the reading experience to qualify as a LitRPG. Typically, this means providing tables to show them frequently."),
    MARTIAL_ARTS(907,"The novel has a focus on any of several arts of combat or self-defense. These may include aikido, karate, judo, or tae kwon do, kendo, fencing, and so on and so forth."),
    MATURE(20,"Contains subject matter which may be too extreme for a younger audience. Content that deals with mature themes such as gore, sex, or violence."),
    MECHA(908,"A work involving and usually concentrating on all types of large robotic machines."),
    MYSTERY(909,"Usually an unexplained event occurs, and the main protagonist attempts to find out what caused it"),
    PSYCHOLOGICAL(910,"Usually deals with the philosophy of a state of mind, in most cases detailing abnormal psychology."),
    ROMANCE(6, "A story in this genre focus heavily on the romantic relationship between two or more people."),
    SCHOOL_LIFE(911,"Having a major setting of the story deal with some type of school."),
    SCI_FI(912,"Short for science fiction, these works involve twists on technology and other science related phenomena which are contrary or stretches of the modern day scientific world."),
    SEINEN(913,"Seinen means 'Young Man.' Novels that specifically targets young adult males around the ages of 18 to 25 are Seinen titles. Typically the story lines deal with the issues of adulthood."),
    SLICE_OF_LIFE(914,"Novels with no focused plot. This genre tends to be naturalistic and mainly focuses around the main characters and their everyday lives. Often there will be some philosophical perspectives regarding love, relationships, life etc. tied into the novel. The overall typical moods for this type of story are cheery and carefree, no-rush life for the characters."),
    SMUT(915,"Erotic work of fiction that's mainly targeted toward females. Smut stories often gives heavy focus on love and relationships between the main characters."),
    SUPERNATURAL(5,"Usually entails amazing and unexplained powers or events which defy the laws of physics."),
    SPORTS(916,"As the name suggests, story with sports as one of its main element. Baseball, basketball, hockey, soccer, golf, and racing just to name a few."),
    TRAGEDY(901,"Contains events resulting in great loss and misfortune.");

    private int id;
    private String description;

    Genre(int id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * Retrieves the GME_ID of the genre, according to ScribbleHub's code.
     * This may be useless for the general user as it will only be used to search through the genres on Series Finder.
     * @return the GME_ID of the genre.
     */
    public int getId(){
        return id;
    }

    /**
     * Returns back the official (and a few genres [unofficial])'s description according to ScribbleHub and NovelUpdates's Genre Definitions.
     * @return the definition of the genre.
     */
    public String getDescription(){
        return description;
    }

}
