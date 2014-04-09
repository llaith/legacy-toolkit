Llaith-Toolkit Project
==================

    Llaith's personal toolkit library.


toolkit-core:
-------------

    Core toolkit classes. Mostly architectural patterns.

* **org.llaith.toolkit.core.controller:**

    Architectural pattern used for implementing views as a
    collection of stacks of controllers that can reject calls to
    dispose themselves. This pattern can represent portal interfaces,
    desktop-like interfaces, and breadcrumb based pages in the same
    way.

* **org.llaith.toolkit.core.dto:**

    A DTO implementation. Dtos have change tracking, a pseudo-type,
    can be nested and support merging values with an origin. On top
    of that there is a more experimental session system which uses a
    stack of collections of Dtos which can push their changes down
    to other Dtos in obscured layers. This is used to minimise
    refreshing in rich guis. Also includes a fluent building mechanism.

* **org.llaith.toolkit.core.fault:**

    Faults are a replacement for simply throwing exceptions. They
    work similar to compiler warnings/errors for use in complex cases
    where one wants to collect errors but continue as far as is
    practical despite failures. They also have the concept of supressing
    errors to allow code that performs validation to be as strict as
    possible and let the caller decide what consitutes a real error
    in that case.

* **org.llaith.toolkit.core.memo:**

    Memos are used for structured text output. They support a common
    subset of output that can be displayed on consoles, but still
    support reasonable structure in html.

* **org.llaith.toolkit.core.pump:**

    A pipes and filters implementation. It is implemented via
    decorators. Supports push and pull and instead of using events
    or additional methods on the interface (such as isOpen()). A stream
    may be converted to and from 'chunks' by the implementation, and a
    robust (can throw errors without closing) and polling (can return
    nothing without closing) approach is supported by returning empty
    chunks to signify no current work on an open source.

* **org.llaith.toolkit.core.registry:**

    A simple registry pattern. Used for type-safe registration of
    implementors of a service interface.

* **org.llaith.toolkit.core.repository:**

    A typesafe collection pattern. Since Guava added FluentIterables, this
    has basically been re-written as a wrapper around that.

* **org.llaith.toolkit.core.stage:**

    An implementation of a set of queues of commands patterns to be
    executed in a controlled manner. Used heavily in batch processing
    codebases. Supports a fluent approach to building. Uses decorators
    to determine if raised exceptions are to cause a failure or a skip.

* **org.llaith.toolkit.core.status:**

    A display abstraction that supports both console and web/desktop
    gui outputters. Particularly useful with used with either the
    stage or the pump implementations.


toolkit-common:
---------------

    Utilities and miscellanious helper/convienence classes. For the most
    part these stand alone and do not deal with any architectural concerns.

* **org.llaith.toolkit.common.depends:**

    A dependency calculator. Calculates reverse dependencies from
    normal dependencies. Pretty clunky and badly needs a refactor but
    does the job for what I used it for (reverse dependencies of
    class heirarchies)

* **org.llaith.toolkit.common.contrib:**

    Stuff written by other without a license/in the public domain.
    Tends to ebb and flow but at the moment contains a great tuples
    implementation found on stack-overflow.

* **org.llaith.toolkit.common.debug:**

    Misc debug utils with norwhere else to live. At the moment has
    a couple of reflection based utils.

* **org.llaith.toolkit.common.exception:**

    Some exception handling utils. This used to be very stable and
    heavily used in my code but over the years Guava started
    containing utils to do the same thing and this was cut down and
    adapter to a few more experimental and less useful leftovers.
    Also contains some common exceptions used throughout my codebase
    like the 'WhoopsException'.

* **org.llaith.toolkit.common.guard:**

    A preconditions utility. Most of the functionality has been
    subsumed with recent versions of Guava, and so now has only a more
    experimental fluent approach based on hamcrest. Use where clarity
    more important than performance, else use the Guava or JDK equivs.

* **org.llaith.toolkit.common.guava:**

    A few common implementations of guava interfaces, including a
    simple bridge between hamcrest matchers and guava predicates.

* **org.llaith.toolkit.common.ident:**

    Used for modules to 'identify' themselves by supplying a more
    human-readable name for themselves to mechanisms that have
    dynamically loaded them. Bit clunky but does the job.

* **org.llaith.toolkit.common.intention:**

    Contains a set of annotations which are used to indicate the
    programmers intentions for a peice of code. Currently I have
    pulled most of these out because they work in tandom with the
    pluggable compiler annotations support which I have not included
    in this current release. I have left the maturity one because
    that is still a good warning without the annotation processor.
    (The annotation processor in this case just emits warnings when
    it finds code marked with a maturity level below release).

* **org.llaith.toolkit.common.lang:**

    A bunch of utils that deal with core SDK classes. Always
    shrinking as Guava grows :)

* **org.llaith.toolkit.common.meta:**

    A metadata implementation that I use heavily when implementing
    meta-models that are shared between modules. Simple but very
    useful.

* **org.llaith.toolkit.common.pattern:**

    A few left over patterns that are too simple to end up in the
    toolkit-core library. Activities, Resources and Commands are
    commonly used in my other codebases.

* **org.llaith.toolkit.common.snapshot:**

    A versioning mechanism I have used for some cases where models
    support partial versioning.

* **org.llaith.toolkit.common.util:**

    Utilities are generally classes made up of purely static functions.

* **org.llaith.toolkit.common.util.jdbc:**

    The only bit still left here is a simple SQLException util. All
    other utils have gone away and been replaced with better 3rd
    party libraries such as sql2o.

* **org.llaith.toolkit.common.util.lang:**

    A few utils for core SDK classes. Some equivalents to Guava or
    common-utils where it would be pointless to bring in a dependency
    for a simle function (like blank string checking).

* **org.llaith.toolkit.common.util.reflection:**

    I do a great deal of work with reflection. Here are some useful
    general utils for helping with that. Like most reflection code,
    it's not pretty. For more complex reflection work I recommend
    something like vidageek-mirror library.

toolkit-dbadapter:
------------------

    Adapters for using specific 3rd party dependencies with the
    toolkit-core patterns.

