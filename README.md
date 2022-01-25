# NaturalLanguageAnalyzer

This program takes natural human sentences and returns object - predicate - subject structures derived from the sentence. Supports complex nested sentence structures and multiple subjects & objects in the same sentence.

---

## Relation object

The **relation object** stores a subject String, an object String, and a predicate String. It represents the structure of word relations within a sentnece, and establishes the relationship between objects and actions.
The program extracts relations by using a Stack to keep track of objects, much like a calculation keeps track of numbers and operations.

---

## Example Usage

The following sentence: "the child saw the cat that the man loved" will return the following output:
```(child,saw,cat) (man,loved,cat)```

This is much like how a human would understand the sentence structure; the man loved the cat, and the child saw the cat.
More tests can easily be completed by adding more sentences to the result list.
