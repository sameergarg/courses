package course.homework;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

/**
 *
 */

public class RemoveHomeworkTest {

    @org.junit.Test
    public void should_remove_grade_of_type_homework_with_lowest_score_for_each_student() throws Exception {
        //given
        MongoClient client = new MongoClient();

        MongoDatabase studentsDb = client.getDatabase("students");

        MongoCollection<Document> gradesCollection = studentsDb.getCollection("grades");


        //with Filter

        //when

        homeworksByScoreAscending(gradesCollection)
                .into(new ArrayList<>())
                .stream()
                .collect(Collectors.groupingBy(
                        (Document grade) -> grade.getDouble("student_id"),
                        Collectors.minBy((Document d1, Document d2) -> d1.getDouble("score").compareTo(d2.getDouble("score")))))
                .values()
                .stream()
                .forEach(toDelete ->
                        toDelete.ifPresent(doc -> gradesCollection.deleteOne(doc))
        );
        //then
    }



    private FindIterable<Document> homeworksByScoreAscending(MongoCollection<Document> gradesCollection) {
        return gradesCollection
                .find(eq("type", "homework"))
                .projection(fields(excludeId(), exclude("type")))
                .sort(orderBy(ascending("student_id"), descending("score")));
    }


}
