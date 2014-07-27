## 1.1.0 (Major API Changes)
* Implement selector for button background #4
* Added Custom Selector Sample
* Replaced `cpb_colorComplete` with `cpb_selectorComplete`
* Replaced `cpb_colorError` with `cpb_selectorError`
* Replaced `cpb_colorIdle` with `cpb_selectorIdle`
* Removed `cpb_colorDisabled` (now you can use above selector)

## 1.0.5

* Implemented #9 Maintain state when rotate screen.
* Fixed issue #17 Setting progress before view was drawn cause unexpected results
* Fixed issue #16 Setting progress to 0 after indeterminate progress cause unexpected results
* Fixed issue #19 Added methods `setBackgroundColor` and `setStrokeColor`
* Fixed issue #21 Progress changes are ignored when the animations are running.
* Added `cpb_colorDisabled` attribute, to change color when you call `setEnabled(false)` method.

## 1.0.4 (Major API Changes)

* Added support to some state changes with animation, eg. Idle to Complete or Idle to Error (Thanks to João Gouveia)
* Implemented morph progress drawable  to different size #14 (Thanks to Balys Valentukevicius)
* Removed 'allowBackup="true"' in AndroidManifest #10
* Added prefix 'cpb_' for all custom attributes to avoid conflict #12
* Moved all samples to separate activities

## 1.0.3

* Implemented indeterminate progress
* Fixed performance issue

## 1.0.2

* Fixed issue when `wrap_content` width was breaking morphing animation

## 1.0.1

* Fixed issue when `app:colorIdle` attribute was ignored

## 1.0.0

* Initial release