package tk.mihou.amatsuki.api;

import tk.mihou.amatsuki.api.connection.AmatsukiConnector;
import tk.mihou.amatsuki.api.enums.*;
import tk.mihou.amatsuki.entities.story.lower.StoryResults;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchFinder {

        // The List of Genres and Content Warnings to use.
        private final Set<Genre> includedGenres = new HashSet<>();
        private final Set<ContentWarning> includedContentWarnings = new HashSet<>();

        // Additional settings.
        private int timeout = 5000;

        // The excluded genres and content warnings to use.
        private final Set<Genre> excludedGenres = new HashSet<>();
        private final Set<ContentWarning> excludedContentWarnings = new HashSet<>();

        // Initiate the initial values in case of when the user doesn't include any of them, will override on later methods.
        private SortBy sorting = SortBy.PAGEVIEWS;
        private boolean order = false;
        private ExclusionMethod genreMethod = ExclusionMethod.OR;
        private StoryStatus storyStatus = StoryStatus.ALL;
        private ExclusionMethod contentWarningMethod = ExclusionMethod.OR;

        /**
         * Which method to sort the stories.
         * @param sorting the sorting of the stories.
         * @return SearchFinder.
         */
        public SearchFinder setSorting(SortBy sorting){
            this.sorting = sorting;

            return this;
        }

        /**
         * Sets whether to sort the list through ascending or descending order.
         * @param ascending ascending = true, descending = false.
         * @return SearchFinder.
         */
        public SearchFinder setAscending(boolean ascending){
            this.order = ascending;

            return this;
        }

        /**
         * Whether to have the genres searched through OR or AND.
         * @param method the method to exclude/include.
         * @return SearchFinder.
         */
        public SearchFinder excludeGenreBy(ExclusionMethod method){
            this.genreMethod = method;

            return this;
        }

        /**
         * Do you wish to only have COMPLETED, HIATUS, ONGOING or ALL stories?
         * @param status the status to use.
         * @return SearchFinder.
         */
        public SearchFinder setStoryStatus(StoryStatus status){
            this.storyStatus = status;

            return this;
        }

        /**
         * Whether to have the content warnings searched through OR or AND.
         * @param method the method to exclude/include.
         * @return SearchFinder.
         */
        public SearchFinder excludeContentWarningBy(ExclusionMethod method){
            this.contentWarningMethod = method;

            return this;
        }

        /**
         * Includes a genre to the included genres for searching.
         * @param genre the genre to include.
         * @return SearchFinder.
         */
        public SearchFinder includeGenre(Genre genre){
            includedGenres.add(genre);

            return this;
        }

        /**
         * Includes a list of genres for searching.
         * @param genres genre to include.
         * @return SearchFinder.
         */
        public SearchFinder includeGenre(Genre... genres){
            includedGenres.addAll(Arrays.asList(genres));

            return this;
        }

        /**
         * Excludes a genre to the included genres for searching.
         * @param genre the genre to exclude.
         * @return SearchFinder.
         */
        public SearchFinder excludeGenre(Genre genre){
            excludedGenres.add(genre);

            return this;
        }

        /**
         * Excludes a list of genres for searching.
         * @param genres genre to exclude.
         * @return SearchFinder.
         */
        public SearchFinder excludeGenre(Genre... genres){
            excludedGenres.addAll(Arrays.asList(genres));

            return this;
        }

        /**
         * Includes a content warning to the included content warnings for searching.
         * @param warning the warning to include.
         * @return SearchFinder.
         */
        public SearchFinder includeContentWarning(ContentWarning warning){
            includedContentWarnings.add(warning);

            return this;
        }

        /**
         * Includes several content warnings to the included content warnings for searching.
         * @param warnings the warnings to include.
         * @return SearchFinder.
         */
        public SearchFinder includeContentWarning(ContentWarning... warnings){
            includedContentWarnings.addAll(Arrays.asList(warnings));

            return this;
        }

        /**
         * Excludes a warning to the excluded warnings for searching.
         * @param warning the warning to exclude.
         * @return SearchFinder.
         */
        public SearchFinder excludeContentWarning(ContentWarning warning){
            excludedContentWarnings.add(warning);

            return this;
        }

        /**
         * Excludes several warnings to the excluded warnings for searching.
         * @param warnings the warnings to exclude.
         * @return SearchFinder.
         */
        public SearchFinder excludeContentWarning(ContentWarning... warnings){
            excludedContentWarnings.addAll(Arrays.asList(warnings));

            return this;
        }

        /**
         * Sets the timeout in case of no results. Optional, since the default is 5 seconds.
         * @param timeout the timeout in millis.
         * @return SearchFinder
         */
        private SearchFinder setTimeout(int timeout){
            this.timeout = timeout;

            return this;
        }

        public CompletableFuture<List<StoryResults>> build(){

            // URL Building...

            // The point of origin of the series finder.
            StringBuilder url = new StringBuilder("https://scribblehub.com/series-finder/?sf=1");

            // Adds all included genres if it isn't empty.
            if(!includedGenres.isEmpty()){
                url.append("&gi=").append(genreTransform(includedGenres));
            }

            if(!includedGenres.isEmpty() || !excludedGenres.isEmpty()){
                url.append("&mgi=").append(genreMethod.getValue());
            }

            // Adds all excluded genres if it isn't empty.
            if(!excludedGenres.isEmpty()){
                url.append("&ge=").append(genreTransform(excludedGenres));
            }

            if(!includedContentWarnings.isEmpty()){
                url.append("&cti=").append(contentTransform(includedContentWarnings));
            }

            if(!includedContentWarnings.isEmpty() || !excludedContentWarnings.isEmpty()){
                url.append("&mct=").append(contentWarningMethod.getValue());
            }

            if(!excludedContentWarnings.isEmpty()){
                url.append("&cte=").append(contentTransform(excludedContentWarnings));
            }

            url.append("&cp=").append(storyStatus.getValue()).append("&sort=").append(sorting.getValue()).append("&order=").append(order ? "asc" : "desc");
            return new AmatsukiConnector().seriesFinderSearch(url.toString(), timeout);
        }

        private String genreTransform(Set<Genre> genres){
            StringBuilder str = new StringBuilder();
            genres.forEach(genre -> str.append(str.length() < 1 ? genre.getId() : ","+genre.getId()));
            return str.toString();
        }

        private String contentTransform(Set<ContentWarning> warnings){
            StringBuilder str = new StringBuilder();
            warnings.forEach(warning -> str.append(str.length() < 1 ? warning.getCtid() : ","+warning.getCtid()));
            return str.toString();
        }

}
