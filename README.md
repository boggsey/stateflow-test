# stateflow-test
Testing updates to deeply nested lists in MutableStateFlow

MutableStateFlow is not triggering a re-compose when a deeply nested value changes. Basically, radio buttons set their value based on their status in state. When one is pressed, it toggles its value in state. Ideally, this should trigger a recompose but it doesn't. Made a little repro here if someone has any thoughts.
