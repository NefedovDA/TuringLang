# Futures

## Common futures

**Default settings of turing machine is:**

* Start state: `S`
* Accept state: `AC`
* Reject state: `RJ`
* Blank symbol: `_`

**Comments and blank lines:**

Empty lines or that start with `--` will be ignored by the compiler.

**State description:**

```$xslt
NAME:
  AT_CHAR OPERATION [TO_STATE [PUT_CHAR]]
  ...
```

Where:

* `NAME` - sequence of latin letters and numbers, starts with a letter.
* `AT_CHAR` - sequence of symbols describing the list of symbols from which 
              this shift could be performed.
              Regex for `AT_CHAR` template: ``"([^\\s`]|`[^\\s]`)+"``
* `OPERATION` - type of shift of turing machine that is one of: 
  * `<` - move to left (back)
  * `>` - move to right (forward)
  * `^` - stop on place
* `TO_STATE` - name of a state in which machine will after a shift. 
               Could be omitted or replace with `.`, by default will be `NAME`.
* `PUT_CHAR` - symbol that will be put at current tape's turing machine of turing machine.
               Could be omitted, default value is symbol at the current cell of tape.
               
## Compiler futures

**Change default turing machine settings:**

 For changing settings use `@<name of directive>: <value>` construct.
 
 Directives:
 * `start` or  `s` - name of the start state.
 * `accept` or `ac` - name of the accept state.
 * `reject` or `rj` - name of the reject state.
 * `blank` or `_` - one symbol that is blank.
 
 **User templates:**
 
 **Adding**
 
 For add some template in program use `@: <name of template> <list of symbols>` construct.
 The name of template should be a one single symbol.  
 
 **Usage**
 
 * For use a template add its name in atChar like: `` `N` ^ ...``, where N is a name of template. 
 * The name is replaced with a template list. 
 * Several names in a row could be combined into one brackets: `` `NMW` ^ ... ``.
 * It could be used with common symbols like: `` _`N`* ^ ... ``
 