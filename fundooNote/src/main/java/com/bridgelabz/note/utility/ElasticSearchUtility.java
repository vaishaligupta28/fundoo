package com.bridgelabz.note.utility;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import com.bridgelabz.note.model.Note;
import com.google.gson.Gson;

public class ElasticSearchUtility {

	private static TransportClient client;
	private static String TITLE = "title";
	private static String DESC = "desc";
	private static String USER_ID = "userId";

	public static void deleteNoteFromElastic(Note note) {
		// TODO Auto-generated method stub

		client = LoadElasticClient.getTransportClient();
		String NOTE_ID = String.valueOf(note.getNoteId());

		DeleteResponse response = client.prepareDelete("collection", "notes", NOTE_ID).get();
	}

	public static List<Note> searchNotesFromElastic(String text, String userId) {

		List<Note> notes;

		client = LoadElasticClient.getTransportClient();

		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery(USER_ID, userId))
				.must(boolQuery().should(matchQuery(TITLE, text)).should(matchQuery(DESC, text)));

		SearchResponse response = client.prepareSearch("collection").setTypes("notes")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(queryBuilder).get();

		System.out.println("Total notes---" + response.getHits().getTotalHits());

		notes = getNotesFromResponse(response);
		return notes;
	}

	public static List<Note> getNotesFromResponse(SearchResponse response) {

		List<Note> notes = new ArrayList<Note>();
		SearchHit[] hits = response.getHits().hits();

		for (SearchHit hit : hits) {
			System.out.println(hit.getSourceAsString());
			String jsonString = hit.getSourceAsString();
			Note note = new Note();
			note = new Gson().fromJson(jsonString, Note.class);
			note.setNoteId(Integer.parseInt(hit.getId()));
			notes.add(note);
		}
		System.out.println(notes);
		return notes;
	}

	public static void createIndexAndLoadData(Note note) {

		System.out.println("trying to load data to elastic search....");
		// Settings settings = Settings.builder().build();

		client = LoadElasticClient.getTransportClient();

		try {
			IndexResponse response = client.prepareIndex("collection", "notes").setId(String.valueOf(note.getNoteId()))
					.setSource(jsonBuilder().startObject().field(USER_ID, note.getUser().getUserId())
							.field(TITLE, note.getTitle()).field(DESC, note.getDesc()).endObject())
					.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}