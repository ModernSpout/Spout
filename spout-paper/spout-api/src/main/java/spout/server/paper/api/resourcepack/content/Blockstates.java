package spout.server.paper.api.resourcepack.content;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * The contents of a {@code blockstates} file in a resource pack.
 */
public final class Blockstates {

    private final JsonObject json;

    private Blockstates(JsonObject json) {
        this.json = json;
    }

    private @Nullable JsonArray getMultipartJsonOrNull() {
        return this.json.getAsJsonArray("multipart");
    }

    private JsonArray getOrCreateMultipartJson() {
        @Nullable JsonArray multipart = this.getMultipartJsonOrNull();
        if (multipart == null) {
            multipart = new JsonArray();
            this.json.add("multipart", multipart);
        }
        return multipart;
    }

    public boolean hasMultipart() {
        @Nullable JsonArray multipart = this.getMultipartJsonOrNull();
        return multipart != null && !multipart.isEmpty();
    }

    public List<JsonObject> getMultipartApplies(BlockData state) {
        @Nullable JsonArray multipart = this.getMultipartJsonOrNull();
        if (multipart == null || multipart.isEmpty()) {
            return Collections.emptyList();
        }
        return multipart.asList().stream()
            .map(JsonElement::getAsJsonObject)
            .filter(multipartElement -> multipartWhenMatches(multipartElement.getAsJsonObject("when"), state))
            .map(multipartElement -> multipartElement.getAsJsonObject("apply"))
            .toList();
    }

    public void addMultipartApply(JsonObject multipartApply, BlockData state) {
        JsonArray multipart = this.getOrCreateMultipartJson();
        JsonObject multipartElement = null;
        for (JsonElement multipartElementCandidate : multipart) {
            JsonObject multipartElementCandidateObject = multipartElementCandidate.getAsJsonObject();
            if (multipartElementCandidateObject.getAsJsonObject("apply").equals(multipartApply)) {
                multipartElement = multipartElementCandidateObject;
                break;
            }
        }
        if (multipartElement == null) {
            multipartElement = new JsonObject();
            multipartElement.add("apply", multipartApply);
            multipart.add(multipartElement);
        }
        JsonObject when = multipartElement.getAsJsonObject("when");
        JsonArray or;
        if (when == null) {
            when = new JsonObject();
            or = new JsonArray();
            when.add("OR", or);
            multipartElement.add("when", when);
        } else {
            or = when.getAsJsonArray("OR");
            if (or == null) {
                if (when.has("AND")) {
                    throw new UnsupportedOperationException("Adding multipart apply currently doesn't support existing AND conditions");
                }
                JsonObject oldWhen = when;
                when = new JsonObject();
                or = new JsonArray();
                or.add(oldWhen);
                when.add("OR", or);
                multipartElement.add("when", when);
            }
        }
        or.add(getMultipartWhenElement(state));
    }

    private @Nullable JsonObject getVariantsJsonOrNull() {
        return this.json.getAsJsonObject("variants");
    }

    private JsonObject getOrCreateVariantsJson() {
        @Nullable JsonObject variants = this.getVariantsJsonOrNull();
        if (variants == null) {
            variants = new JsonObject();
            this.json.add("variants", variants);
        }
        return variants;
    }

    public boolean hasVariants() {
        @Nullable JsonObject variants = this.getVariantsJsonOrNull();
        return variants != null && !variants.isEmpty();
    }

    private @Nullable JsonObject getVariantJsonOrNull(String variantKey) {
        @Nullable JsonObject variants = this.getVariantsJsonOrNull();
        if (variants == null) return null;
        return variants.getAsJsonObject(variantKey);
    }

    private JsonObject getOrCreateVariantJson(String variantKey) {
        JsonObject variants = this.getOrCreateVariantsJson();
        @Nullable JsonObject variantJson = variants.getAsJsonObject(variantKey);
        if (variantJson == null) {
            variantJson = new JsonObject();
            variants.add(variantKey, variantJson);
        }
        return variantJson;
    }

    public Map<String, JsonObject> getVariants() {
        @Nullable JsonObject variants = this.getVariantsJsonOrNull();
        if (variants == null) {
            return Collections.emptyMap();
        }
        Map<String, JsonElement> variantsMap = variants.asMap();
        Map<String, JsonObject> resultMap = new HashMap<>(variantsMap.size());
        for (Map.Entry<String, JsonElement> entry : variantsMap.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue().getAsJsonObject());
        }
        return resultMap;
    }

    public @Nullable JsonObject getVariant(String variantKey) {
        return this.getVariantJsonOrNull(variantKey);
    }

    public @Nullable JsonObject getVariant(BlockData state) {
        String variantKey = getVariantKey(state);
        @Nullable JsonObject direct = this.getVariant(variantKey);
        if (direct != null) return direct;
        // Try less-complete variant keys
        @Nullable JsonObject variants = this.getVariantsJsonOrNull();
        if (variants == null) return null;
        Set<String> variantKeyElements = Set.of(variantKey.split(","));
        for (Map.Entry<String, JsonElement> entry : variants.asMap().entrySet()) {
            if (entry.getKey().isBlank()) {
                return entry.getValue().getAsJsonObject();
            }
            String[] elements = entry.getKey().split(",");
            boolean containsAllElements = true;
            for (String element : elements) {
                if (!variantKeyElements.contains(element)) {
                    containsAllElements = false;
                    break;
                }
            }
            if (containsAllElements) {
                return entry.getValue().getAsJsonObject();
            }
        }
        return null;
    }

    public void setVariant(String variantKey, JsonObject variantJson) {
        JsonObject variants = this.getOrCreateVariantsJson();
        variants.add(variantKey, variantJson);
    }

    public void setVariant(BlockData state, JsonObject variantJson) {
        this.setVariant(getVariantKey(state), variantJson);
    }

    public void setVariantToModel(String variantKey, String model) {
        JsonObject variantJson = new JsonObject();
        variantJson.addProperty("model", model);
        this.setVariant(variantKey, variantJson);
    }

    public void setVariantToModel(BlockData state, String model) {
        this.setVariantToModel(getVariantKey(state), model);
    }

    public void setVariantToModel(String variantKey, NamespacedKey model) {
        this.setVariantToModel(variantKey, model.toString());
    }

    public void setVariantToModel(BlockData state, NamespacedKey model) {
        this.setVariantToModel(getVariantKey(state), model);
    }

    public JsonObject getJson() {
        return this.json;
    }

    @Override
    public int hashCode() {
        return this.json.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Blockstates other && this.json.equals(other.json);
    }

    @Override
    public String toString() {
        return this.json.toString();
    }

    private static Map<String, String> getStateValuesMap(BlockData state) {
        Map<String, String> map = new Object2ObjectArrayMap<>(6);
        {
            StringTokenizer tokenizer = new StringTokenizer(getVariantKey(state), ",");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                int equalsIndex = token.indexOf('=');
                map.put(token.substring(0, equalsIndex), token.substring(equalsIndex + 1));
            }
        }
        return map;
    }

    private static boolean multipartWhenMatches(JsonObject multipartWhen, BlockData state) {
        JsonElement or = multipartWhen.get("OR");
        if (or != null) {
            return or.getAsJsonArray().asList().stream().anyMatch(listElement -> multipartWhenMatches(listElement.getAsJsonObject(), state));
        }
        JsonElement and = multipartWhen.get("AND");
        if (and != null) {
            return and.getAsJsonArray().asList().stream().allMatch(listElement -> multipartWhenMatches(listElement.getAsJsonObject(), state));
        }
        Map<String, String> stateValues = getStateValuesMap(state);
        List<String> reusableValuesList = new ArrayList<>(5);
        for (Map.Entry<String, JsonElement> entry : multipartWhen.entrySet()) {
            reusableValuesList.clear();
            StringTokenizer tokenizer = new StringTokenizer(entry.getValue().getAsString(), "|");
            while (tokenizer.hasMoreTokens()) {
                reusableValuesList.add(tokenizer.nextToken());
            }
            if (!reusableValuesList.contains(stateValues.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    private static JsonObject getMultipartWhenElement(BlockData state) {
        JsonObject element = new JsonObject();
        getStateValuesMap(state).forEach(element::addProperty);
        return element;
    }

    public static Blockstates ofImmutable(JsonObject jsonObject) {
        return new Blockstates(jsonObject.deepCopy());
    }

    public static Blockstates ofMutable(JsonObject jsonObject) {
        return new Blockstates(jsonObject);
    }

    public static Blockstates create() {
        return new Blockstates(new JsonObject());
    }

    public static String getVariantKey(BlockData state) {
        String string = state.getAsString(false);
        int openBracketIndex = string.indexOf('[');
        if (openBracketIndex == -1) {
            return "";
        }
        return string.substring(openBracketIndex + 1, string.length() - 1).replace(" ", "");
    }

}
